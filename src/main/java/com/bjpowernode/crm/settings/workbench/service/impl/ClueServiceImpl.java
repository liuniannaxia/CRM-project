package com.bjpowernode.crm.settings.workbench.service.impl;

import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.base.util.DateTimeUtil;
import com.bjpowernode.crm.base.util.UUIDUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import com.bjpowernode.crm.settings.workbench.domain.*;
import com.bjpowernode.crm.settings.workbench.mapper.*;
import com.bjpowernode.crm.settings.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClueServiceImpl implements ClueService {
    @Autowired
    private ClueMapper clueMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private ClueActivityMapper clueActivityMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private ContactsMapper contactsMapper;
    @Autowired
    private CustomerRemarkMapper customerRemarkMapper;
    @Autowired
    private ClueRemarkMapper clueRemarkMapper;
    @Autowired
    private ContactsRemarkMapper contactsRemarkMapper;
    @Autowired
    private ContactsActivityRelationMapper contactsActivityRelationMapper;
    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private TransactionHistoryMapper transactionHistoryMapper;
    @Autowired
    private TransactionRemarkMapper transactionRemarkMapper;


    @Override
    public void createClue(Clue clue) {

        clueMapper.insertSelective(clue);

    }

    @Override
    public List<Clue> createClues(Clue clue) {
        //判断各个条件是否满足(模糊查询)
        Example example = new Example(Clue.class);
        Example.Criteria criteria = example.createCriteria();

        if(clue.getFullname()!=null&&clue.getFullname()!=""){
            criteria.andLike("name","%"+clue.getFullname()+"%");
        }
        if(clue.getCompany()!=null&&clue.getCompany()!="") {
            criteria.andLike("company", "%" + clue.getCompany() + "%");
        }
        if(clue.getPhone()!=null&&clue.getPhone()!="") {
            criteria.andLike("phone", "%" + clue.getPhone() + "%");
        }
        if(clue.getSource()!=null&&clue.getSource()!="") {
            criteria.andEqualTo("source", clue.getSource() );
        }
        if(clue.getOwner()!=null&&clue.getOwner()!="") {
            /*思路
            1.先得到线索用户输入的名字，去用户表中模糊查询出该名字对应的主键，
                这个主键就是线索表的外键（owner）
            2.根据这个owner去线索表查询出对应的线索表的信息
            * */

            Example example1 = new Example(User.class);
            Example.Criteria criteria1 = example1.createCriteria();
            criteria1.andLike("name","%"+clue.getOwner()+"%");
            //返回的是一个user对象，从这个user对象里面获得user的id号
            List<User> users = userMapper.selectByExample(example1);
            List<String> ids = new ArrayList<>();
            for (User user : users) {
                ids.add(user.getId());
                //clueMapper.selectByExample(user.getId());
            }
            criteria1.andIn("owner",ids);
        }
        if(clue.getMphone()!=null&&clue.getMphone()!="") {
            criteria.andLike("mphone", "%" + clue.getMphone() + "%");
        }
        if(clue.getSource()!=null&&clue.getSource()!="") {
            criteria.andEqualTo("state", clue.getState());
        }

        List<Clue> clues = clueMapper.selectByExample(example);


        return clues;
    }

    @Override
    public List<Activity> queryClueActivity(String id) {
        //已知线索的id，查询出中间表的全部内容,再根据中间表查询出市场活动的id，然后再查出所有的市场活动
        ClueActivity clueActivity = new ClueActivity();
        //把已知的线索id设置进去
        clueActivity.setClueId(id);
        //下面返回的是一个List集合，再遍历这个集合，获得市场活动id
        List<ClueActivity> activities = clueActivityMapper.select(clueActivity);
        List<String> aids = new ArrayList<>();
        for (ClueActivity clueActivity1 : activities) {
            aids.add(clueActivity1.getActivityId());
        }
        Example example = new Example(Activity.class);
        example.createCriteria().andIn("id",aids);
        List<Activity> activitys = activityMapper.selectByExample(example);

        return activitys;
    }

    @Override
    public List<Activity> queryAllActivitys(String name, String id) {
        Example example = new Example(Activity.class);
        Example.Criteria criteria = example.createCriteria();
        if (name != null && name != "") {
            //查询出已经关联的市场活动（查中间表）
            ClueActivity clueActivity = new ClueActivity();
            clueActivity.setClueId(id);
            List<ClueActivity> clueActivities = clueActivityMapper.select(clueActivity);
            //取出中间表的所有市场活动id，放到集合中
            List<String> ids = new ArrayList<>();
            for (ClueActivity activity1 : clueActivities) {
                ids.add(activity1.getActivityId());
            }

            List<Activity> activities1 = queryClueActivity(id);
            criteria.andLike("name", "%" + name + "%").andNotIn("id",ids);
            //Activity activity = activityMapper.selectByPrimaryKey(id);
        }
        //已经关联过的不显示
        List<Activity> activities = activityMapper.selectByExample(example);

          return activities;
    }

    @Override
    public void insertClueActivity(String ids, String id) {


        String[] idss = ids.split(",");
        for (String s : idss) {
            ClueActivity clueActivity = new ClueActivity();
            clueActivity.setActivityId(s);
            clueActivity.setClueId(id);
            clueActivity.setId(UUIDUtil.getUUID());
            clueActivityMapper.insert(clueActivity);
        }
    }

    @Override
    public void deleteClueActivity(String id, String activityId) {

        ClueActivity clueActivity = new ClueActivity();
        clueActivity.setClueId(id);
        clueActivity.setActivityId(activityId);
        clueActivityMapper.delete(clueActivity);
    }

    @Override
    public Clue queryById(String id) {

        Clue clue = clueMapper.selectByPrimaryKey(id);
        return clue;
    }

    @Override
    public void convert(String clueId,String isCreateTransaction,Transaction transaction) {
        //先将线索中的客户信息取出来保存在客户表中（先查出来），当该客户不存在的时候，新建客户(按公司名称精准查询)
        Clue clue = queryById(clueId);
        //再把客户查询出来，才能知道客户存在不存在
        String company = clue.getCompany();
        Customer customer = new Customer();
        customer.setName(company);
        List<Customer> customers = customerMapper.select(customer);
        if (customers.size()==0){
            //客户不存在，新建客户
            customer.setId(UUIDUtil.getUUID());
            customer.setCreateTime(DateTimeUtil.getSysTime());
            customer.setAddress(clue.getAddress());
            customer.setOwner(clue.getOwner());
            customer.setCreateBy(clue.getCreateBy());
            customer.setContactSummary(clue.getContactSummary());
            //其他字段可以在客户模块进行编辑
            customerMapper.insertSelective(customer);
        }else {
             customer = customers.get(0);
        }
        //将线索的联系人信息取出来，保存在联系人表中
        Contacts contacts = new Contacts();
        contacts.setId(UUIDUtil.getUUID());
        contacts.setAppellation(clue.getAppellation());
        contacts.setId(UUIDUtil.getUUID());
        contacts.setCreateBy(clue.getCreateBy());
        contacts.setCustomerId(customer.getId());
        contactsMapper.insertSelective(contacts);

        //4、线索的备注信息取出来，保存在客户备注和联系人备注表中
        //保存客户备注
        CustomerRemark customerRemark = new CustomerRemark();
        customerRemark.setId(UUIDUtil.getUUID());
        customerRemark.setCustomerId(customer.getId());
        customerRemark.setCreateBy(clue.getCreateBy());
        customerRemark.setCreateTime(DateTimeUtil.getSysTime());
        customerRemarkMapper.insertSelective(customerRemark);
        //保存联系人备注
        ContactsRemark contactsRemark = new ContactsRemark();
        contactsRemark.setId(UUIDUtil.getUUID());
        contactsRemark.setContactsId(contacts.getId());
        contactsRemark.setCreateBy(clue.getCreateBy());
        contactsRemark.setCreateTime(DateTimeUtil.getSysTime());
        contactsRemarkMapper.insertSelective(contactsRemark);

        //5.将线索和市场关系转换到联系人和市场关系


        ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
        //先根据传过来的线索id号查询对应的市场活动
        ClueActivity clueActivity = new ClueActivity();
        clueActivity.setClueId(clueId);
        List<ClueActivity> clueActivities = clueActivityMapper.selectAll();
        for (ClueActivity activity : clueActivities) {
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setActivityId(activity.getId());
            contactsActivityRelation.setContactsId(contacts.getId());
            contactsActivityRelationMapper.insertSelective(contactsActivityRelation);
        }

        //6、如果发生交易，将交易信息保存在交易表中
        if ("1".equals(isCreateTransaction)){
            //发生交易
            //Transaction transaction = new Transaction();
            transaction.setId(UUIDUtil.getUUID());
            transaction.setCreateTime(DateTimeUtil.getSysTime());
            transaction.setContactsId(contacts.getId());
            transaction.setCustomerId(customer.getId());
            //创建者肯定是当前登录用户，在session里面获取,直接在控制层设置了
            transactionMapper.insertSelective(transaction);
        }
        //7、创建交易历史，一个交易可以对应多个历史
        //保存交易历史信息
        //最好把创建者从控制层传过来，在这里设置
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setId(UUIDUtil.getUUID());
        transactionHistory.setCreateBy(DateTimeUtil.getSysTime());
        transactionHistory.setExpectedDate(transaction.getExpectedDate());
        transactionHistory.setMoney(transaction.getMoney());
        transactionHistory.setStage(transaction.getStage());
        transactionHistory.setTranId(transaction.getId());
        transactionHistoryMapper.insertSelective(transactionHistory);

        //保存交易备注信息
        TransactionRemark transactionRemark = new TransactionRemark();
        transactionRemark.setId(UUIDUtil.getUUID());
        transactionRemark.setCreateTime(DateTimeUtil.getSysTime());
        transactionRemark.setTranId(transaction.getId());
        transactionRemarkMapper.insertSelective(transactionRemark);

        //先删子表再删主表
        //8、删除线索的备注信息(线索的备注是一对多)
        ClueRemark clueRemark = new ClueRemark();
        clueRemark.setClueId(clueId);
        clueRemarkMapper.delete(clueRemark);

        //9、删除线索的和市场活动的关联关系
        ClueActivity clueActivity1 = new ClueActivity();
        clueActivity1.setClueId(clueId);
        clueActivityMapper.delete(clueActivity1);

        //10、删除线索信息
        clueMapper.deleteByPrimaryKey(clueId);
    }

    @Override
    public List<Activity> queryRelationActivity(String id,String name) {
        //先根据线索市场活动中间表查询 线索关联的市场活动id号 ， 在根据id号查询市场活动
        ClueActivity clueActivity = new ClueActivity();
        clueActivity.setClueId(id);
        List<ClueActivity> clueActivities = clueActivityMapper.select(clueActivity);
        //因为最终返回的是一个list集合，所以创建一个集合把查询到的市场活动放进去
        List<Activity> aids = new ArrayList();

        for (ClueActivity clueActivity1 : clueActivities) {
            Activity activity = activityMapper.selectByPrimaryKey(clueActivity1.getActivityId());
            //变相的实现模糊查询
            if (activity.getName().contains(name)){
                aids.add(activity);
            }
        }
        return aids;
    }


}


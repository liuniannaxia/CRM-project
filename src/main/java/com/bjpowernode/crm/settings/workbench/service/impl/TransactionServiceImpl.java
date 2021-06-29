package com.bjpowernode.crm.settings.workbench.service.impl;

import com.bjpowernode.crm.base.bean.BarVo;
import com.bjpowernode.crm.base.util.DateTimeUtil;
import com.bjpowernode.crm.base.util.UUIDUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.workbench.domain.Customer;
import com.bjpowernode.crm.settings.workbench.domain.Stage;
import com.bjpowernode.crm.settings.workbench.domain.Transaction;
import com.bjpowernode.crm.settings.workbench.domain.TransactionHistory;
import com.bjpowernode.crm.settings.workbench.mapper.CustomerMapper;
import com.bjpowernode.crm.settings.workbench.mapper.TransactionHistoryMapper;
import com.bjpowernode.crm.settings.workbench.mapper.TransactionMapper;
import com.bjpowernode.crm.settings.workbench.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private TransactionHistoryMapper transactionHistoryMapper;

    @Override
    public List<String> queryCustomerName(String customerName) {
        Example example = new Example(Customer.class);
        example.createCriteria().andLike("name","%"+customerName+"%");
        List<Customer> customers = customerMapper.selectAll();
        //放到一个list集合中
        List<String> customerNames = new ArrayList<>();
        for (Customer customer : customers) {
            customerNames.add(customer.getName());
        }

        return customerNames;
    }

    @Override
    public Transaction queryDetail(String id) {
        Transaction transaction = transactionMapper.selectByPrimaryKey(id);

        //查询交易历史信息
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setTranId(id);
        List<TransactionHistory> transactionHistories = transactionHistoryMapper.select(transactionHistory);
        //因为transaction类里面注入了transactionHistory对象，才能这样设置
        transaction.setTransactionHistories(transactionHistories);
        return transaction;
    }

    @Override
    public Map<String,Object> stageList(String id, Map<String,String> stage2Possibility, Integer position, User user) {

        //定义一个list集合，用来存储每次循环后的对象
        List<Stage> stages = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();


        //查询当前阶段所对应图标和可能性
        Transaction transaction = transactionMapper.selectByPrimaryKey(id);
        String currentStage = transaction.getStage();
        String currentPossibility = transaction.getPossibility();

        //查询所有阶段的图标和可能性，从session里面获取
        //首先遍历map集合，先把map转换成list，因为list有下标索引,map底层就是entry
        //          先转换成set集合，set集合可以直接放到list集合中
        Set<Map.Entry<String, String>> entries = stage2Possibility.entrySet();
        List<Map.Entry<String, String>> list = new ArrayList<>(entries);

        //确定当前阶段在所有阶段中的索引位置（用possible判断）
        int index = 0;
        if (position == null){
            for (int i = 0;i<list.size();i++) {
                Map.Entry<String, String> entry = list.get(i);
                String possibility = entry.getValue();
                if (currentPossibility.equals(possibility)){
                    index = i;
                    break;
                }
            }
        }else {
            //点了图标
            index = position;
            Map.Entry<String, String> entry = list.get(index);
            currentStage = entry.getKey();
            currentPossibility = entry.getValue();
            Transaction t = new Transaction();
            t.setId(id);
            t.setPossibility(currentPossibility);
            t.setStage(currentStage);
            transactionMapper.updateByPrimaryKeySelective(t);

            //查询阶段历史信息
            TransactionHistory transactionHistory = new TransactionHistory();
            transactionHistory.setStage(currentStage);
            transactionHistory.setId(UUIDUtil.getUUID());
            transactionHistory.setTranId(id);
            transactionHistory.setMoney(transaction.getMoney());
            transactionHistory.setExpectedDate(transaction.getExpectedDate());
            transaction.setCreateBy(user.getName());
            transactionHistory.setCreateTime(DateTimeUtil.getSysTime());
            transactionHistory.setPossibility(transaction.getPossibility());
            transactionHistoryMapper.insertSelective(transactionHistory);
            map.put("transactionHistory",transactionHistory);

        }

        int point = 0;

        //确定所有阶段可能性为0的第一个索引的位置
        for (int i = 0;i<list.size();i++) {
            Map.Entry<String, String> entry = list.get(i);
            String possibility = entry.getValue();
            if (possibility.equals("0")){
                point = i;
                break;
            }
        }


        if ("0".equals(currentPossibility)){
            //交易失败，红叉在哪个位置不知道
            //获取所有的阶段（遍历list集合）
            for (int i = 0;i<list.size();i++){
                Map.Entry<String, String> entry = list.get(i);
                String stage = entry.getKey();
                String possibility = entry.getValue();
                //循环9次，每次循环都有新的对象
                Stage stage1 = new Stage();

                if (possibility.equals("0") ){
                    if (currentStage.equals(stage)){

                        stage1.setType("红x");
                    }else {

                        stage1.setType("黑x");
                    }
                }else {

                    stage1.setType("黑x");
                }
                stage1.setPossibility(possibility);
                stage1.setStage(stage);
                stage1.setIndex(i+"");
                stages.add(stage1);
            }

        }else {
            //交易中状态，但是不能判断是那种情况
            //只有当前端传过来索引位置才能判断
            for (int i = 0;i<list.size();i++){
                Map.Entry<String, String> entry = list.get(i);
                String stage = entry.getKey();
                String possibility = entry.getValue();
                Stage stage1 = new Stage();


                if (i<index){

                    stage1.setType("绿圈");
                }else if(i == index){
                    stage1.setType("锚点");
                }else if (i > index && i < point){
                    stage1.setType("黑圈");
                }else {
                    stage1.setType("黑x");
                }

                stage1.setPossibility(possibility);
                stage1.setStage(stage);
                stage1.setIndex(i+"");
                stages.add(stage1);

            }
        }
        map.put("stages",stages);
        return map;
    }

    @Override
    public BarVo transactionEcharts() {
        BarVo<Object> objectBarVo = new BarVo<>();
        List<BarVo> barVo = transactionMapper.transactionEcharts();
        List<Integer> data = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        List<Object> result = new ArrayList<>();
        for (BarVo vo : barVo) {
            titles.add(vo.getTitles());
            data.add(vo.getAmount());
        }
        result.add(titles);
        result.add(data);
        objectBarVo.setT(result);
        return  objectBarVo;
    }
}

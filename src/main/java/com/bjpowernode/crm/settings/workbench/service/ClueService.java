package com.bjpowernode.crm.settings.workbench.service;

import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.settings.workbench.domain.Activity;
import com.bjpowernode.crm.settings.workbench.domain.Clue;
import com.bjpowernode.crm.settings.workbench.domain.Transaction;

import java.util.List;

public interface ClueService {
    void createClue(Clue clue);

    List<Clue> createClues(Clue clue);

    List<Activity> queryClueActivity(String id);

    List<Activity> queryAllActivitys(String name, String id);

    void insertClueActivity(String ids, String id);

    void deleteClueActivity(String id, String activityId);

    Clue queryById(String id);

    void convert(String clueId, String isCreateTransaction, Transaction transaction);

    List<Activity> queryRelationActivity(String id,String name);
}

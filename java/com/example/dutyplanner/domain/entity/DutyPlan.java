package com.example.dutyplanner.domain.entity;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.HashMap;

public class DutyPlan {

    //cols совпадает с числом дней месяца
    //rows совпадает с числом пользователей администратора

    private String[][] plan;
    public DutyPlan (int rows, int cols){

        plan = new String[rows][cols];

    }

    public DutyPlan (String [][] plan){

        this.plan = plan;

    }

    public void addRow()
    {
        String[][] tempPlan = plan.clone();

        plan = new String[tempPlan.length+1][tempPlan[0].length];

        for (int i=0; i<tempPlan.length; i++)
        {
            System.arraycopy(tempPlan[i], 0, plan[i], 0, tempPlan[0].length);
        }

    }

    public void removeRow(int index)
    {
        String[][] tempPlan = plan.clone();

        plan = new String[tempPlan.length-1][tempPlan[0].length];

        int i_temp=0, j_temp=0;
        for (int i=0; i<tempPlan.length; i++)
        {
            for (int j=0; j<tempPlan[0].length; j++)
            {
                if (i!=index) {
                    plan[i_temp][j_temp] = tempPlan[i][j];
                    j_temp++;
                }
            }
            j_temp=0;
            if (i!=index)
                i_temp++;
        }
    }

    public void updatePlan(String[][] newPlan)
    {
        plan=newPlan;
    }

    public String[][] getPlan() {return plan;}
    public int getRows() {return plan.length;}
    public int getCols(){return plan[0].length;}


}

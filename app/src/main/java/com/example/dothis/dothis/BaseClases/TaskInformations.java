package com.example.dothis.dothis.BaseClases;

/**
 * Created by Valentin on 1/14/2017.
 */

public class TaskInformations {
    private String objectiveText;
    private String dueDate;

    public TaskInformations(String objectiveText, String dueDate){
        this.objectiveText = objectiveText;
        this.dueDate = dueDate;
    }

    public String getObjectiveText() {
        return objectiveText;
    }

    public void setObjectiveText(String objectiveText) {
        this.objectiveText = objectiveText;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}

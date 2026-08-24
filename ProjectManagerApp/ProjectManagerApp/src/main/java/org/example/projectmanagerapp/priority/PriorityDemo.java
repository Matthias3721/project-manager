package org.example.projectmanagerapp.priority;

public class PriorityDemo {

    public static void main(String[] args) {
        PriorityLevel priority = new MediumPriority();

        System.out.println(priority.getPriority());
    }
}
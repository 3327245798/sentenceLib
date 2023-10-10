package com.blp.sentenceLib.constant;

public enum JobType {

    INTENSE(100, "即时型任务"),
    PERIOD(200, "周期型任务"),
    DELAY(300, "延迟型任务"),

    COMMAND_WITH_RESULT(1, "命令型-有返回值"),
    COMMAND_WITHOUT_RESULT(2, "命令型-无返回值"),

    DELAY_SCHEDULE(3, "定时型-延时"),
    PERIOD_SCHEDULE(4, "定时型-周期"),
    PERIOD_SCHEDULE_WITH_CONDITION(40, "定时型-周期-带判断条件"),

    TOOL_ACTION_LISTEN(5, "监听型-工具执行"),
    TOOL_EXCEPTION_LISTEN(6, "监听型-工具异常"),
    TOOL_INTERRUPT_LISTEN(7, "监听型-中断信号"),

    AUTHORISE_WITHOUT_RESULT(8, "授权型-无返回值");

    private int type;
    private String discription;

    JobType(int type, String discription) {
        this.type = type;
        this.discription = discription;
    }

    public int getType() {
        return type;
    }
    public String getDiscription(int type) {
        JobType[] jobTypes = JobType.values();
        for(JobType jobType:jobTypes){
            if(jobType.type==type){
                return jobType.discription;
            }
        }
        try {
            throw new Exception("不存在的jobType");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public String getDiscription() {
        return discription;
    }
}

package com.aias.learn.design.pattern.adapter;

/**
 * @author liuhy
 * @since 2020/4/23
 * 适配器模式
 */
public class Phone {

    public static final int VOLTAGE = 220;
    private VoltageAdapter adapter;

    public void charge() {
        adapter.changeVoltage();
    }

    public void setAdapter(VoltageAdapter adapter) {
        this.adapter = adapter;
    }

    // 变压器
    static class VoltageAdapter {
        // 改变电压的功能
        public void changeVoltage() {
            System.out.println("正在充电...");
            System.out.println("原始电压：" + Phone.VOLTAGE + "V");
            System.out.println("经过变压器转换之后的电压:" + (Phone.VOLTAGE - 200) + "V");
        }
    }

    public static void main(String[] args) {
        Phone phone = new Phone();
        VoltageAdapter voltageAdapter = new Phone.VoltageAdapter();
        phone.setAdapter(voltageAdapter);
        phone.charge();
    }
}

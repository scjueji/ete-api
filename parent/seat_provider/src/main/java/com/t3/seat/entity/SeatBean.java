package com.t3.seat.entity;

/**
 * Created by Administrator on 2020/1/2.
 */
public class SeatBean {
    private String seat_id;

    private Integer status;

    public String getSeat_id() {
        return seat_id;
    }

    public SeatBean(String seat_id, Integer status) {
        this.seat_id = seat_id;
        this.status = status;
    }

    public void setSeat_id(String seat_id) {
        this.seat_id = seat_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SeatBean{" +
                "seat_id='" + seat_id + '\'' +
                ", status=" + status +
                '}';
    }
}

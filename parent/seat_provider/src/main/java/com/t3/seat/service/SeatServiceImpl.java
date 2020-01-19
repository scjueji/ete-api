package com.t3.seat.service;

import com.t3.seat.entity.SeatBean;
import com.t3.service.SeatService;

/**
 * Created by Administrator on 2020/1/6.
 */
public class SeatServiceImpl implements SeatService{
    @Override
    public String getSeatInfo(String seatId) {
        return new SeatBean("033203",1).toString();
    }
}

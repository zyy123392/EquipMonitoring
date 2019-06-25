package com.zyy.service;

import java.io.IOException;

public interface AirConditionService {
    String turnDown(int tem) throws IOException;
    String turnOn() throws IOException;
    String turnUp(int tem) throws IOException;
    String turnOff() throws IOException;
}

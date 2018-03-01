package com.chengyi.app.listener;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public interface INet {

    void  response(int tag, Object o)  ;
      void  fetch(int tag);

    int FIRST=1;
    int SECOND=2;
    int THIRD=3;
    int FOURTH=4;

}

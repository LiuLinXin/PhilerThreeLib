package com.example.testsome.designmode.mvvm;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

/**
 * Created by philer on 2019/7/10.
 * Describe:
 */
public class BeanMVVM extends BaseObservable {
    @Bindable
    public String name;

    public int age;
    public String address;

    public BeanMVVM(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }
}

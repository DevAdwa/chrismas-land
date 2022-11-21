package com.example.fecafootdemo.ui.base;

import com.example.fecafootdemo.data.CoreDataManager;

/*
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/28/22)
 */
public class BasePresenter<V extends BaseView> implements IBase<V> {
    V v;
    public CoreDataManager coreDataManager;
    public BasePresenter(V v) {
        this.v = v;
        coreDataManager = CoreDataManager.getInstance();
    }
    @Override
    public V getView() {
        return v;
    }
}

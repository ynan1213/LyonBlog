package com.ynan._01.demo;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Action2;

/**
 * @Author yuannan
 * @Date 2022/1/5 16:24
 */
public class SimpleDemo {

    public static void main(String[] args) {

//        Observable<String> sender = Observable.create(new OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                subscriber.onNext("Hi,111");
//                subscriber.onNext("Hi,222");
//                subscriber.onNext("Hi,333");
//                subscriber.onCompleted();
//            }
//        });

        Observable<String> sender = Observable.just("s1", "s2");

        Observer<String> receiver = new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError");
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext :" + s);
            }
        };
        sender.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

            }
        });
    }
}

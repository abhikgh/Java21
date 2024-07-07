package com.src.stream;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observables.ConnectableObservable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class RxJavaEx {

    public static void main(String[] args) throws InterruptedException {

        String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h", "i"};

        // Observable :: non-backpressured abstract class to consume consume synchronous / asynchronous reactive dataflows.
        /*
            public abstract class Observable<T>
            extends Object
            implements ObservableSource<T>

            All Implemented Interfaces:
            ObservableSource<T>
            Direct Known Subclasses:
            ConnectableObservable, GroupedObservable, Subject

            Rx is single-threaded which implies that an Observable and the chain of operators 
            that we can apply to it will notify its observers on the same thread on which its subscribe() method is called
        
        */
        
        Observable.fromArray(letters)
                .map(String::toUpperCase)
                .subscribe(st -> System.out.println(st), //onNext
                        Throwable::printStackTrace,      //onError
                        () -> {});                       //onComplete
        // A B C D E F G H I

        Observable.fromArray(letters)
                .scan(new StringBuilder(), StringBuilder::append) //scan(R initialValue, accumulator)
                .subscribe(st -> System.out.println(st),
                        Throwable::printStackTrace,
                        () -> {});
        //a ab abc abcd abcde abcdef abcdefg abcdefgh abcdefghi

        Observable.fromArray(letters)
                .defaultIfEmpty("Observable is empty")
                .firstElement()
                .subscribe(st -> System.out.println(st),
                        Throwable::printStackTrace,
                        () -> {}); //a

        Integer[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        Observable.fromArray(numbers)
                .groupBy(i -> i % 2 == 0 ? "EVEN" : "ODD")
                .subscribe(group -> group.subscribe((number) -> {
                            System.out.print(number + "-" + group.getKey() + " ");
                        },
                        Throwable::printStackTrace,
                        () -> {}
                ));
        //0-EVEN 1-ODD 2-EVEN 3-ODD 4-EVEN 5-ODD 6-EVEN 7-ODD 8-EVEN 9-ODD

        System.out.println();
        List<Integer> listEven = Observable.fromArray(numbers)
                .filter(i -> i%2==0)
                .collect(Collectors.toList()).blockingGet();
        System.out.println(listEven);
        //[0, 2, 4, 6, 8]

        List<Integer> firstFiveNos = Observable.fromArray(numbers)
                .takeWhile(i -> i<5)
                .collect(Collectors.toList()).blockingGet();
        System.out.println(firstFiveNos);
        //[0, 1, 2, 3, 4]

        String[] result = {""};
        ConnectableObservable<Long> connectableObservable = Observable.interval(200, TimeUnit.MILLISECONDS).publish();
        connectableObservable.subscribe(i -> result[0] += i);
        System.out.println(result[0]);

        connectableObservable.connect();
        Thread.sleep(500);

        System.out.println(result[0]); //01

        List<String> lettersString = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i");
        Observable.fromIterable(lettersString)
                .map(String::toUpperCase)
                .subscribe(System.out::println);

        System.out.println("//------------------- fromIterable :: Custom function ----------------------------");

        Observable.fromIterable(lettersString)
                .map(s->updateLetter(s))
                .subscribe(System.out::println);

        System.out.println("//------------------- Cold Observable ----------------------------");

        Observable observable = Observable.create(source -> {
            source.onNext(Math.random());
        });
        observable.subscribe(System.out::println);
        observable.subscribe(System.out::println);

        System.out.println("//------------------- Hot Observable ----------------------------");

        var i = Math.random();
        Observable observable2 = Observable.create(source -> {
            source.onNext(i);
        });
        observable2.subscribe(System.out::println);
        observable2.subscribe(System.out::println);

        System.out.println("//------------------- concatArrayDelayError ----------------------------");

        AtomicReference<String> ipAddress = new AtomicReference<>("");
        Map<String,String> map1 = new HashMap<>();
        map1.put("net-ip","333");map1.put("ip-address","132333");
        Map<String,String> map2 = new HashMap<>();
        map2.put("hostname","kekdkd");map2.put("ip-address","232233");map2.put("operating-system","win11");

    
        //concatArrayDelayError
        //Concatenates a variable number of ObservableSource sources and delays errors from any of them till all terminate

        //concatArrayEagerDelayError
        //Concatenates an array of ObservableSources eagerly into a single stream of values and delaying any errors until all sources terminate
        Observable.concatArrayEagerDelayError(getIpFromMap1(map1), getIpFromMap2(map2))
                .subscribe(ipAddressVal -> ipAddress.set(ipAddressVal));
        System.out.println(ipAddress.get()); //232233
    }


    private static Observable<String> getIpFromMap1(Map<String, String> map1) {
        return Observable.fromIterable(map1.entrySet())
                .filter(entry -> entry.getKey().equals("ip-address"))
                .firstElement()
                .map(Map.Entry::getValue)
                .flatMapObservable(Observable::just);
    }

    private static Observable<String> getIpFromMap2(Map<String, String> map2) {
        return Observable.fromIterable(map2.entrySet())
                .filter(entry -> entry.getKey().equals("ip-address"))
                .firstElement()
                .map(Map.Entry::getValue)
                .flatMapObservable(Observable::just);
    }


    private static String updateLetter(String s) {
        return s+"AA";
    }

}

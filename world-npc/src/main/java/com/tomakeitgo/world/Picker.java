package com.tomakeitgo.world;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Picker {

    private final Random random;

    public Picker() {
        random = new Random();
    }

    public <T> T pick(List<T> toPick, List<Integer> weights){
        var total = weights.stream().reduce(0, Integer::sum);

        var number = random.nextInt(total);

        int i = 0;
        for (int j = 0; j < weights.size(); j++) {
            Integer weight = weights.get(j);
            if (number < weight + i){
                return toPick.get(j);
            }
            i += weight;
        }

        throw new IllegalStateException("Shouldn't happen,");
    }

    public static void main(String[] args) {
        var picker = new Picker();

        HashMap<Integer, Integer> hist = new HashMap<>();
        for (int i = 0; i < 12000; i++) {
            var item = picker.pick(List.of(-1,0,1), List.of(10,10,10));
            hist.put(item, hist.getOrDefault(item, 0) + 1);
        }
        System.out.println(hist);
    }
}

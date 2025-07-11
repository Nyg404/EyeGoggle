package io.github.nyg404.eyegoggle.api.point;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

public class ListExperiencePoint {
    public Map<ExperiencePoint, Integer> listPoint;

    public static final Codec<Map<ExperiencePoint, Integer>> MAP_CODEC = Codec.unboundedMap(ExperiencePoint.CODEC, Codec.INT);
    public static final Codec<ListExperiencePoint> CODEC = MAP_CODEC.xmap(ListExperiencePoint::new,
            ListExperiencePoint::getListPoint);

    public ListExperiencePoint(){
        this.listPoint = new LinkedHashMap<>();
    }

    public ListExperiencePoint(Map<ExperiencePoint, Integer> listPoint){
        this.listPoint = new LinkedHashMap<>(listPoint);
    }

    public Map<ExperiencePoint, Integer> getListPoint(){
       return listPoint;
    }

    public Integer getAmount(){
        return listPoint.values().stream().mapToInt(Integer::intValue).sum();
    }

    public ListExperiencePoint add(ExperiencePoint experiencePoint, Integer amount){
       return builder().copyOf(this).add(experiencePoint, amount).build();
    }

    public Builder builder(){
        return new Builder();
    }

    public static class Builder{
        public Map<ExperiencePoint, Integer> listPointB = new LinkedHashMap<>();

        public Builder copyOf(ListExperiencePoint c){
            listPointB.clear();
            listPointB.putAll(c.getListPoint());
            return this;
        }

        public Builder add(ExperiencePoint experiencePoint, Integer amount){
            listPointB.merge(experiencePoint, amount, Integer::sum);
            return this;
        }

        public ListExperiencePoint build(){
            return new ListExperiencePoint(listPointB);
        }
    }
}

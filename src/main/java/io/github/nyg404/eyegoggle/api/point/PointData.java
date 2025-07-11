package io.github.nyg404.eyegoggle.api.point;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;


public record PointData(int amount, int trebamount, int level) {
    public static final Codec<PointData> CODEC = RecordCodecBuilder.create(pointDataInstance -> pointDataInstance.group(
            Codec.INT.fieldOf("amount").forGetter(PointData::amount),
            Codec.INT.fieldOf("treb").forGetter(PointData::trebamount),
            Codec.INT.fieldOf("level").forGetter(PointData::level)
    ).apply(pointDataInstance, PointData::new));


}


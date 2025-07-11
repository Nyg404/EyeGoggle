package io.github.nyg404.eyegoggle.api.point;


import java.util.Map;

public interface IContainerExperiencePoint {
    Map<ExperiencePoint, PointData> getPoints();

    void add(ExperiencePoint experiencePoint, int amount);
    void subtract(ExperiencePoint experiencePoint, int amount);

    Integer getAmount(ExperiencePoint experiencePoint);
    Integer getLevel(ExperiencePoint experiencePoint);
}

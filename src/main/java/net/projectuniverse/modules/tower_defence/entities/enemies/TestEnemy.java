package net.projectuniverse.modules.tower_defence.entities.enemies;

import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.ai.EntityAIGroupBuilder;
import net.minestom.server.entity.ai.goal.FollowTargetGoal;

import java.time.Duration;

public class TestEnemy extends EntityCreature {
    public TestEnemy(EntityCreature target) {
        super(EntityType.SKELETON);
        addAIGroup(new EntityAIGroupBuilder().addGoalSelector(new FollowTargetGoal(this, Duration.ofSeconds(6))).build());
        setTarget(target);
        setSprinting(false);
    }
}

package me.youngermax.customitems.deathscythe;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class Scythe {
    public static final float BASE_RADIUS = 2.5f;
    public static final int MAX_AGE = 20 * 4;
    private final Player thrower;

    public double velocity;
    public double yaw; // looking side to side
    public double pitch; // looking up and down
    public long age;
    public Location currentLocation;

    public Scythe(Player thrower, double velocity, double yaw, double pitch, Location currentLocation) {
        this.thrower = thrower;
        this.velocity = velocity;

        // This math does 2 things.
        // 1. Add 90 degrees to the yaw so that the scythe will be inline with the direction that the player is facing
        // 2. Convert to radians to be used in below formulas
        this.yaw = Math.toRadians(yaw + 90);

        this.pitch = Math.toRadians(pitch + 90);

        this.currentLocation = currentLocation;
    }

    public void tick() {
        this.age += 1;
        this.spawnParticles();
        this.moveForward();
        this.damageClosePlayers();
    }

    private void damageClosePlayers() {
        double radius = BASE_RADIUS * inOutAnimation(getLifePercentage());

        this.currentLocation.getWorld().getPlayers().forEach(player -> {
            if (player != thrower && radius > player.getLocation().distance(this.currentLocation)) {
                player.damage(10);
            }
        });
    }

    private void moveForward() {
        this.velocity *= 0.93d;
        this.currentLocation.add(Math.cos(yaw) * velocity, Math.cos(pitch) * velocity, Math.sin(yaw) * velocity);
    }

    private void spawnParticles() {
        int currentDegree = (int) ((-age * 50) % 360);
        double radius = BASE_RADIUS * inOutAnimation(getLifePercentage());

        // Animate only 60 degrees of the circle
        for (float degree = currentDegree; currentDegree + 180 > degree; degree++) {
            double radian = Math.toRadians(degree);

            currentLocation.getWorld().spawnParticle(
                    Particle.SPELL_WITCH,
                    this.currentLocation.getX() + (radius * Math.cos(radian) * Math.cos(yaw)),
                    this.currentLocation.getY() + (radius * Math.sin(radian)),
                    this.currentLocation.getZ() + (radius * Math.cos(radian) * Math.sin(yaw)),
                    0,
                    0,
                    0,
                    0
            );
        }
    }

    private double getLifePercentage() {
        return (double) this.age / (double) MAX_AGE;
    }

    private double inOutAnimation(double x) {
        final int speed = 10;
        return -1 * Math.cos(speed * Math.PI * x) * x + 1;
    }
}

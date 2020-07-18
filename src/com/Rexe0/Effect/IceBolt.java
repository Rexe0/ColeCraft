package com.Rexe0.Effect;

import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.util.MathUtils;
import org.bukkit.Particle;
import de.slikey.effectlib.util.RandomUtils;
import de.slikey.effectlib.util.VectorUtils;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class IceBolt extends Effect {

    /**
     * ParticleType of spawned particle
     */
    public Particle particle = Particle.FIREWORKS_SPARK;

    /**
     * Growing per iteration in the length (0.05)
     */
    public float lengthGrow = 0.2f;

    public Location beamLocation;

    /**
     * Radials per iteration to spawn the next particle (PI / 16)
     */
    public double angularVelocity = Math.PI / 16;

    /**
     * Cone-particles per interation (10)
     */
    public int particles = 10;

    /**
     * Growth in blocks per iteration on the radius (0.006)
     */
    public float radiusGrow = 0.0005f;

    /**
     * Conesize in particles per cone
     */
    public int particlesCone = 240;

    /**
     * Start-angle or rotation of the cone
     */
    public double rotation = 0;

    /**
     * Randomize every cone on creation (false)
     */
    public boolean randomize = false;

    /**
     * Current step. Works as counter
     */
    protected int step = 0;

    private Player player;

    public IceBolt(EffectManager effectManager, Player player) {
        super(effectManager);
        type = EffectType.REPEATING;
        period = 1;
        this.player = player;
        iterations = 200;
    }

    @Override
    public void reset() {
        this.step = 0;
    }

    @Override
    public void onRun() {
        Location location = getLocation();
        for (int x = 0; x < particles; x++) {
            if (step > particlesCone) {
                step = 0;
            }
            if (randomize && step == 0) {
                rotation = RandomUtils.getRandomAngle();
            }
            double angle = step * angularVelocity + rotation;
            float radius = step * radiusGrow;
            float length = step * lengthGrow;
            Vector v = new Vector(Math.cos(angle) * radius, length, Math.sin(angle) * radius);
            VectorUtils.rotateAroundAxisX(v, (location.getPitch() + 90) * MathUtils.degreesToRadians);
            VectorUtils.rotateAroundAxisY(v, -location.getYaw() * MathUtils.degreesToRadians);


            location.add(v);

            this.beamLocation = location;


            if (location.getWorld().getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ()).getType().isSolid()) {
                this.cancel();
            }

            display(particle, location);
            display(Particle.SNOWBALL, location);



            location.subtract(v);



            step++;
        }
    }
}
package com.legendary.items.entity;

import com.legendary.items.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ThunderAxeEntity extends ThrownItemEntity {

	private static final double KNOCKBACK_RADIUS = 5.0;
	private static final double KNOCKBACK_STRENGTH = 1.6;
	private static final float HIT_DAMAGE = 9.0f;

	public ThunderAxeEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
		super(entityType, world);
	}

	public ThunderAxeEntity(World world, LivingEntity owner) {
		super(ModEntities.THUNDER_AXE_ENTITY, owner, world, new ItemStack(ModItems.THUNDER_AXE));
	}

	public ThunderAxeEntity(World world, double x, double y, double z) {
		super(ModEntities.THUNDER_AXE_ENTITY, x, y, z, world, new ItemStack(ModItems.THUNDER_AXE));
	}

	@Override
	protected Item getDefaultItem() {
		return ModItems.THUNDER_AXE;
	}

	@Override
	protected void onEntityHit(EntityHitResult hitResult) {
		super.onEntityHit(hitResult);

		if (!getEntityWorld().isClient()) {
			Entity hitEntity = hitResult.getEntity();
			Entity owner = getOwner();

			if (hitEntity instanceof LivingEntity living) {
				DamageSource source = owner != null
						? getDamageSources().thrown(this, owner)
						: getDamageSources().generic();
				living.damage((ServerWorld) getEntityWorld(), source, HIT_DAMAGE);
			}

			strikeLightningAndKnockback(hitResult.getPos());
		}
	}

	@Override
	protected void onBlockHit(BlockHitResult hitResult) {
		super.onBlockHit(hitResult);
		if (!getEntityWorld().isClient()) {
			strikeLightningAndKnockback(hitResult.getPos());
		}
	}

	private void strikeLightningAndKnockback(Vec3d hitPos) {
		World world = getEntityWorld();
		if (world instanceof ServerWorld serverWorld) {

			LightningEntity bolt = EntityType.LIGHTNING_BOLT.create(serverWorld, net.minecraft.entity.SpawnReason.TRIGGERED);
			if (bolt != null) {
				bolt.refreshPositionAfterTeleport(hitPos);
				bolt.setCosmetic(false);
				serverWorld.spawnEntity(bolt);
			}

			Entity owner = getOwner();
			if (owner != null) {
				Box area = owner.getBoundingBox().expand(KNOCKBACK_RADIUS);
				for (LivingEntity mob : world.getEntitiesByClass(LivingEntity.class, area, e -> e != owner && e.isAlive())) {
					double dx = mob.getX() - owner.getX();
					double dz = mob.getZ() - owner.getZ();
					mob.takeKnockback(KNOCKBACK_STRENGTH, -dx, -dz);
					mob.velocityModified = true;
				}
			}
		}

		this.discard();
	}
}

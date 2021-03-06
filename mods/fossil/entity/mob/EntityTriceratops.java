package mods.fossil.entity.mob;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import mods.fossil.Fossil;
import mods.fossil.client.DinoSoundHandler;
import mods.fossil.fossilAI.DinoAIAttackOnCollide;
import mods.fossil.fossilAI.DinoAIControlledByPlayer;
import mods.fossil.fossilAI.DinoAIEat;
import mods.fossil.fossilAI.DinoAIFollowOwner;
import mods.fossil.fossilAI.DinoAIGrowup;
import mods.fossil.fossilAI.DinoAIStarvation;
import mods.fossil.fossilAI.DinoAIWander;
import mods.fossil.fossilEnums.EnumDinoFoodBlock;
import mods.fossil.fossilEnums.EnumDinoFoodItem;
import mods.fossil.fossilEnums.EnumDinoType;
import mods.fossil.fossilEnums.EnumOrderType;
import mods.fossil.guiBlocks.GuiPedia;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class EntityTriceratops extends EntityDinosaur
{
    private boolean looksWithInterest;
    //public final float HuntLimit = (float)(this.getHungerLimit() * 4 / 5);
    /*private float field_25048_b;
    private float field_25054_c;
    private boolean field_25052_g;*/
    //public int RushTick = 0;
    public boolean Running = false;

    public EntityTriceratops(World var1)
    {
        super(var1,EnumDinoType.Triceratops);
        this.OrderStatus = EnumOrderType.FreeMove;
        this.looksWithInterest = false;
        //this.health = 8;
        //this.experienceValue=3;
        
        /*this.Width0=1.2F;
        this.WidthInc=0.4F;
        this.Length0=1.1F;
        this.LengthInc=0.7F;
        this.Height0=1.2F;
        this.HeightInc=0.36F;
        
        /*this.HitboxXfactor=5.0F;
        this.HitboxYfactor=5.0F;
        this.HitboxZfactor=5.0F;*
        
        this.BaseattackStrength=3;
        //this.AttackStrengthIncrease=;
        //this.BreedingTime=;
        //this.BaseSpeed=;
        this.SpeedIncrease=0.016F;
        this.MaxAge=13;
        this.BaseHealth=21;
        this.HealthIncrease=1;
        //this.AdultAge=;
        //this.AgingTicks=;
        this.MaxHunger=500;
        //this.Hungrylevel=;*/
        this.updateSize();
        
        this.setSubSpecies((new Random()).nextInt(3) + 1);
        this.getNavigator().setAvoidsWater(true);
        //this.tasks.addTask(0, new DinoAIGrowup(this));
        //this.tasks.addTask(0, new DinoAIStarvation(this));
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.ridingHandler = new DinoAIControlledByPlayer(this));//, 0.34F));
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(4, new DinoAIAttackOnCollide(this, true));
        this.tasks.addTask(5, new DinoAIFollowOwner(this, 5.0F, 2.0F));
        //this.tasks.addTask(6, new DinoAIEatFerns(this));
        //this.tasks.addTask(6, new DinoAIUseFeeder(this, 24, EnumDinoEating.Herbivorous));
        this.tasks.addTask(7, new DinoAIEat(this, 24));
        this.tasks.addTask(8, new DinoAIWander(this));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(10, new EntityAILookIdle(this));
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean isAIEnabled()
    {
        return !this.isModelized();
    }

    /**
     * Returns the texture's file path as a String.
     */
    public String getTexture()
    {
        if (this.isModelized())
            return super.getTexture();
        if(this.isAdult())
        {
        	switch (this.getSubSpecies())
            {
                case 1:
                    return "/mods/fossil/textures/mob/Triceratops_Adult_1.png";

                case 2:
                    return "/mods/fossil/textures/mob/Triceratops_Adult_2.png";

                case 3:
                    return "/mods/fossil/textures/mob/Triceratops_Adult_3.png";

                default:
                    return "/mods/fossil/textures/mob/Triceratops_Adult_1.png";
            }
        }
        if(this.isTeen())
        {
        	switch (this.getSubSpecies())
            {
                case 1:
                    return "/mods/fossil/textures/mob/Triceratops_Teen_1.png";

                case 2:
                    return "/mods/fossil/textures/mob/Triceratops_Teen_2.png";

                case 3:
                    return "/mods/fossil/textures/mob/Triceratops_Teen_3.png";

                default:
                    return "/mods/fossil/textures/mob/Triceratops_Teen_1.png";
            }
        }
		switch (this.getSubSpecies())
        {
            case 1:
                return "/mods/fossil/textures/mob/Triceratops_Baby_1.png";

            case 2:
                return "/mods/fossil/textures/mob/Triceratops_Baby_2.png";

            case 3:
                return "/mods/fossil/textures/mob/Triceratops_Baby_3.png";

            default:
                return "/fossil/textures/mob/Triceratops_Baby_1.png";
        }

    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    /*public void writeEntityToNBT(NBTTagCompound var1)
    {
        super.writeEntityToNBT(var1);
        //var1.setInteger("SubSpecies", this.getSubSpecies());
        //var1.setBoolean("Angry", this.isSelfAngry());
    }*/

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    /*public void readEntityFromNBT(NBTTagCompound var1)
    {
        super.readEntityFromNBT(var1);
        //this.setSubSpecies(var1.getInteger("SubSpecies"));
        //this.CheckSkin();
        //this.setSelfAngry(var1.getBoolean("Angry"));
        //this.InitSize();
    }*/
    /**
     * Causes this entity to do an upwards motion (jumping).
     */
    protected void jump()
    {
        this.motionY = 0.5;
        this.isAirBorne = true;
        ForgeHooks.onLivingJump(this);
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return this.worldObj.getClosestPlayerToEntity(this, 8.0D) != null ? DinoSoundHandler.tri_roar : null;
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
    	return DinoSoundHandler.tri_death;
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return DinoSoundHandler.tri_death;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();
        /*this.field_25054_c = this.field_25048_b;

        if (this.looksWithInterest)
        {
            this.field_25048_b += (1.0F - this.field_25048_b) * 0.4F;
        }
        else
        {
            this.field_25048_b += (0.0F - this.field_25048_b) * 0.4F;
        }*/

        if (this.looksWithInterest)
        {
            this.numTicksToChaseTarget = 10;
        }
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
    }

    /*public float getInterestedAngle(float var1)
    {
        return (this.field_25054_c + (this.field_25048_b - this.field_25054_c) * var1) * 0.15F * (float)Math.PI;
    }*/

    public float getEyeHeight()
    {
        return this.height * 0.8F;
    }

    /**
     * The speed it takes to move the entityliving's rotationPitch through the faceEntity method. This is only currently
     * use in wolves.
     */
    public int getVerticalFaceSpeed()
    {
        return this.isSitting() ? 20 : super.getVerticalFaceSpeed();
    }

    /**
     * Disables a mob's ability to move on its own while true.
     */
    protected boolean isMovementCeased()
    {
        return this.isSitting();// || this.field_25052_g;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource var1, int var2)
    {
        if (this.modelizedDrop())
        {
            return true;
        }
        else
        {
            Entity var3 = var1.getEntity();

            if (var3 != null && !(var3 instanceof EntityPlayer) && !(var3 instanceof EntityArrow))
            {
                var2 = (var2 + 1) / 2;
            }

            if (!super.attackEntityFrom(var1, var2))
            {
                return false;
            }
            else
            {
                if (!this.isTamed() && !this.isSelfAngry())
                {
                    if (var3 instanceof EntityPlayer)
                    {
                        this.setSelfAngry(true);
                        this.entityToAttack = var3;
                    }

                    if (var3 instanceof EntityArrow && ((EntityArrow)var3).shootingEntity != null)
                    {
                        var3 = ((EntityArrow)var3).shootingEntity;
                    }
                }
                else if (var3 != this && var3 != null)
                {
                    if (this.isTamed() && var3 instanceof EntityPlayer && ((EntityPlayer)var3).username.equalsIgnoreCase(this.getOwnerName()))
                    {
                        return true;
                    }

                    this.entityToAttack = var3;
                }

                return true;
            }
        }
    }

    /**
     * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
     * (Animals, Spiders at day, peaceful PigZombies).
     */
    protected Entity findPlayerToAttack()
    {
        return this.isSelfAngry() ? this.worldObj.getClosestPlayerToEntity(this, 16.0D) : null;
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer var1)
    {
    	//Add special item interaction code here
        return super.interact(var1);
    }

    public boolean isSelfAngry()
    {
        return (this.dataWatcher.getWatchableObjectByte(16) & 2) != 0;
    }

    /*public boolean isSelfSitting()
    {
        return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
    }*/

    public void setSelfAngry(boolean var1)
    {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);

        if (var1)
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 2)));
            this.moveSpeed = 2.0F;
        }
        else
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -3)));
            this.moveSpeed = 0.5F;
        }
    }

    /*public void setSelfSitting(boolean var1)
    {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);

        if (var1)
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 1)));
        }
        else
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -2)));
        }
    }

    public void setTamed(boolean var1)
    {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);

        if (var1)
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 4)));
        }
        else
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -5)));
        }
    }*/

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        return this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0 && !this.worldObj.isAnyLiquid(this.boundingBox);
    }

   /* private void InitSize()
    {
        this.updateSize();
        this.setPosition(this.posX, this.posY, this.posZ);
        this.moveSpeed = this.getSpeed();//0.5F + (float)(this.getDinoAge() * 3);
    }*/

    /*private void ChangeTexture()
    {
        this.CheckSkin();
    }*/

    public void updateRiderPosition()
    {
        if (this.riddenByEntity != null)
        {
            this.riddenByEntity.setPosition(this.posX, this.posY + (double)this.getDinoHeight() * 0.65D + 0.07D * (double)(12 - this.getDinoAge()), this.posZ);
        }
    }

    /*public boolean HandleEating(int var1)
    {
        return this.HandleEating(var1, false);
    }

    public boolean HandleEating(int var1, boolean var2)
    {
        if (this.getHunger() >= this.getHungerLimit())
        {
            if (this.isTamed() && !var2)
            {
                this.SendStatusMessage(EnumSituation.Full, this.SelfType);
            }

            return false;
        }
        else
        {
            this.increaseHunger(var1);
            this.showHeartsOrSmokeFX(false);

            if (this.getHunger() >= this.getHungerLimit())
            {
                this.setHunger(this.getHungerLimit());
            }

            return true;
        }
    }*/

    /*private boolean FindWheats(int var1)
    {DEAD CODE; replaced by DinoAIPickItem
        if (this.isSelfSitting())//Sitting->Can't eat
        {
            return false;
        }
        else
        {
            List var2 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(1.0D, 0.0D, 1.0D));//Sits on Food?

            if (var2 != null)
            {
                for (int var3 = 0; var3 < var2.size(); ++var3)
                {
                    if (var2.get(var3) instanceof EntityItem)
                    {
                        EntityItem var4 = (EntityItem)var2.get(var3);

                        if (this.CheckEatable(var4.func_92014_d().itemID))// == Item.wheat.itemID
                        {
                            this.HandleEating(10);
                            this.worldObj.playSoundAtEntity(this, "random.pop", 0.2F, (((new Random()).nextFloat() - (new Random()).nextFloat()) * 0.7F + 1.0F) * 2.0F);
                            var4.setDead();
                            return true;
                        }
                    }
                }
            }

            EntityItem var8 = null;//Looking for food...
            List var9 = this.worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(this.posX, this.posY, this.posZ, this.posX + 1.0D, this.posY + 1.0D, this.posZ + 1.0D).expand((double)var1, 4.0D, (double)var1));
            Iterator var5 = var9.iterator();

            while (var5.hasNext())
            {
                Entity var6 = (Entity)var5.next();

                if (var6 instanceof EntityItem)
                {
                    EntityItem var7 = (EntityItem)var6;

                    if (this.CheckEatable(var7.func_92014_d().getItem().itemID))// == Item.wheat.itemID		//Is item eatable?
                    {
                        if (var8 != null)
                        {
                            if (this.GetDistanceWithEntity(var7) < this.GetDistanceWithEntity(var8))
                            {//go to nearer food
                                var8 = var7;
                            }
                        }
                        else
                        {//it found food!
                            var8 = var7;
                        }
                    }
                }
            }

            if (var8 != null)
            {//go to the food
                this.setPathToEntity(this.worldObj.getPathEntityToEntity(this, var8, (float)var1, true, false, true, false));
                return true;
            }
            return false;//poor dino, no food :(
        }
    }*/

    /**
     * Applies a velocity to each of the entities pushing them away from each other. Args: entity
     */
    public void applyEntityCollision(Entity var1)
    {
        if (var1 instanceof EntityLiving && this.riddenByEntity != null && this.onGround && this.RiderSneak==true)
        {//you can hurt others with the dino while you ride it
            //this.onKillEntity((EntityLiving)var1);
            ((EntityLiving)var1).attackEntityFrom(DamageSource.causeMobDamage(this), 10);
        }
        else
        {
            super.applyEntityCollision(var1);
        }
    }

    
    public int BlockInteractive()
    {
    	int destroyed=0;
    	
    	if (!this.isAdult() && !Fossil.FossilOptions.Dino_Block_Breaking)
    	{
	        for (int var1 = (int)Math.round(this.boundingBox.minX) - 1; var1 <= (int)Math.round(this.boundingBox.maxX) + 1; ++var1)
	        {
	            for (int var2 = (int)Math.round(this.boundingBox.minY); var2 <= (int)Math.round(this.boundingBox.maxY); ++var2)
	            {
	                for (int var3 = (int)Math.round(this.boundingBox.minZ) - 1; var3 <= (int)Math.round(this.boundingBox.maxZ) + 1; ++var3)
	                {
	                    if (!this.worldObj.isAirBlock(var1, var2, var3))
	                    {
	                        int var4 = this.worldObj.getBlockId(var1, var2, var3);

	                        if (!this.inWater)
	                        {
	                            /*if (this.isTamed() && this.riddenByEntity == null)
	                            {
	                                if (var4 == Block.wood.blockID || var4 == Block.leaves.blockID)
	                                {
	                                    this.worldObj.setBlockWithNotify(var1, var2, var3, 0);
	                                    this.RushTick = 10;
	                                }
	                            }
	                            else*/ if ((double)Block.blocksList[var4].getBlockHardness(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ) <= 1.5D || var4 == Block.wood.blockID || var4 == Block.planks.blockID || var4 == Block.woodDoubleSlab.blockID || var4 == Block.woodSingleSlab.blockID)
	                            {
	                                if ((new Random()).nextInt(10) == 5)
	                                {
	                                    Block.blocksList[var4].dropBlockAsItem(this.worldObj, var1, var2, var3, 1, 0);
	                                }

	                                this.worldObj.setBlock(var1, var2, var3, 0);
	                                destroyed++;
	                                //this.RushTick = 10;
	                            }
	                        }
	                    }
	                }
	            }
	        }
    	}
        return destroyed;
    }

    /*public boolean isWheat(ItemStack var1)
    {Not needed, use CheckEatable instead
        return var1 != null && var1.itemID == Item.carrot.itemID;
    }*/

    /*public void ShowPedia(EntityPlayer var1)
    {
        this.PediaTextCorrection(this.SelfType, var1);

        if (this.isTamed())
        {
            Fossil.ShowMessage(OwnerText + this.getOwnerName(), var1);
            Fossil.ShowMessage(AgeText + this.getDinoAge(), var1);
            Fossil.ShowMessage(HelthText + this.health + "/" + 20, var1);
            Fossil.ShowMessage(HungerText + this.getHunger() + "/" + this.MaxHunger, var1);

            if (this.isTamed() && this.getDinoAge() > 4 && this.riddenByEntity == null)
            {
                Fossil.ShowMessage(RidiableText, var1);
            }
        }
        else
        {
            Fossil.ShowMessage(UntamedText, var1);
        }
    }*/
    public EntityTriceratops spawnBabyAnimal(EntityAgeable var1)
    {
        return new EntityTriceratops(this.worldObj);
    }

    private boolean FindFren(int var1)
    {
        float var2 = (float)(var1 * 2);
        int var3 = 0;
        int var4 = 0;
        int var5 = 0;
        int var6;
        int var7;

        for (var6 = -var1; var6 <= var1; ++var6)
        {
            for (var7 = -2; var7 <= 2; ++var7)
            {
                for (int var8 = -var1; var8 <= var1; ++var8)
                {
                    if (this.worldObj.getBlockId((int)Math.round(this.posX + (double)var6), (int)Math.round(this.posY + (double)var7), (int)Math.round(this.posZ + (double)var8)) == Fossil.ferns.blockID && var2 > this.GetDistanceWithXYZ(this.posX + (double)var6, this.posY + (double)var7, this.posZ + (double)var8))
                    {
                        var2 = this.GetDistanceWithXYZ(this.posX + (double)var6, this.posY + (double)var7, this.posZ + (double)var8);
                        var3 = var6;
                        var4 = var7;
                        var5 = var8;
                    }
                }
            }
        }

        if (var2 == (float)(var1 * 2))
        {
            return false;
        }
        else if (Math.sqrt((double)(var3 ^ 2 + var4 ^ 2 + var5 ^ 2)) >= 2.0D)
        {
            this.setPathToEntity(this.worldObj.getEntityPathToXYZ(this, (int)Math.round(this.posX + (double)var3), (int)Math.round(this.posY + (double)var4), (int)Math.round(this.posZ + (double)var5), 10.0F, true, false, true, false));
            return true;
        }
        else
        {
            this.FaceToCoord((int)(-(this.posX + (double)var3)), (int)(this.posY + (double)var4), (int)(-(this.posZ + (double)var5)));
            this.increaseHunger(10);

            for (var6 = -1; var6 <= 1; ++var6)
            {
                for (var7 = -1; var7 <= 1; ++var7)
                {
                    if (this.worldObj.getBlockId((int)Math.round(this.posX + (double)var3 + (double)var6), (int)Math.round(this.posY + (double)var4), (int)Math.round(this.posZ + (double)var5 + (double)var7)) == Fossil.ferns.blockID)
                    {
                        this.worldObj.playAuxSFX(2001, (int)Math.round(this.posX + (double)var3 + (double)var6), (int)Math.round(this.posY + (double)var4), (int)Math.round(this.posZ + (double)var5 + (double)var7), Block.tallGrass.blockID);
                        this.worldObj.setBlock((int)Math.round(this.posX + (double)var3 + (double)var6), (int)Math.round(this.posY + (double)var4), (int)Math.round(this.posZ + (double)var5 + (double)var7), 0);

                        if (this.worldObj.getBlockId((int)Math.round(this.posX + (double)var3 + (double)var6), (int)Math.round(this.posY + (double)var4) + 1, (int)Math.round(this.posZ + (double)var5 + (double)var7)) == Fossil.ferns.blockID)//fernUpper
                        {
                            this.worldObj.setBlock((int)Math.round(this.posX + (double)var3 + (double)var6), (int)Math.round(this.posY + (double)var4) + 1, (int)Math.round(this.posZ + (double)var5 + (double)var7), 0);
                        }

                        if (this.worldObj.getBlockId((int)Math.round(this.posX + (double)var3 + (double)var6), (int)Math.round(this.posY + (double)var4) - 1, (int)Math.round(this.posZ + (double)var5 + (double)var7)) == Block.grass.blockID)
                        {
                            this.worldObj.setBlock((int)Math.round(this.posX + (double)var3 + (double)var6), (int)Math.round(this.posY + (double)var4) - 1, (int)Math.round(this.posZ + (double)var5 + (double)var7), Block.dirt.blockID);
                        }
                    }
                }

                this.heal(3);
                this.setPathToEntity((PathEntity)null);
            }

            return true;
        }
    }

    /*public void updateSize()
    {
        this.setSize((float)(1.5D + 0.3D * (double)((float)this.getDinoAge())), (float)(1.5D + 0.3D * (double)((float)this.getDinoAge())));
    }*/
    
    /*protected int foodValue(Item var1)
    {
        return var1 == Item.wheat ? 10 : (var1 == Item.appleRed ? 30 : 0);
    }

    public void HoldItem(Item var1) {}*/

    /*public float getGLX()
    {
        return (float)(1.5D + 0.3D * (double)((float)this.getDinoAge()));
    }

    public float getGLY()
    {
        return (float)(1.5D + 0.3D * (double)((float)this.getDinoAge()));
    }*/

    /*public String[] additionalPediaMessage()
    {
        String[] var1 = null;

        if (!this.isTamed())
        {
            var1 = new String[] {UntamedText};
        }
        else
        {
            ArrayList var2 = new ArrayList();

            if (this.isTamed() && this.getDinoAge() > 4 && this.riddenByEntity == null)
            {
                var2.add(RidiableText);
            }

            if (!var2.isEmpty())
            {
                var1 = new String[1];
                var1 = (String[])var2.toArray(var1);
            }
        }

        return var1;
    }*/

    /*public EntityAgeable func_90011_a(EntityAgeable var1)
    {
        return this.spawnBabyAnimal(var1);
    }*/

	@Override
	public EntityAgeable createChild(EntityAgeable var1) 
	{
		return null;
	}
}

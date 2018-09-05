package xyz.brassgoggledcoders.steamagerevolution.modules.worldgen;

import com.google.common.collect.Lists;
import com.teamacronymcoders.base.registrysystem.config.ConfigRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

import java.util.ArrayList;
import java.util.Random;

public class OreGenerator implements IWorldGenerator {

    public static ArrayList<OreEntry> oresToGenerate = Lists.newArrayList();

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
                         IChunkProvider chunkProvider) {
        switch (world.provider.getDimension()) {
            case -1:
                break;
            case 0:
                generateSurface(world, random, chunkX * 16, chunkZ * 16);
                break;
            case 1:
                break;
        }
    }

    // The actual generation method.
    private void generateSurface(World world, Random rand, int chunkX, int chunkZ) {
        for (OreEntry entry : oresToGenerate) {
            if (SteamAgeRevolution.instance.getRegistry(ConfigRegistry.class, "CONFIG")
                    .getBoolean("do" + entry.config + "OreGen", true)) {
                for (int k = 0; k < entry.rarity; k++) {
                    int startX = chunkX + rand.nextInt(16);
                    int startZ = chunkZ + rand.nextInt(16);
                    int startY = rand.nextInt(entry.maxY);
                    BlockPos startPos = new BlockPos(startX, startY, startZ);
                    new WorldGenMinable(entry.ore, entry.veinSize).generate(world, rand, startPos);
                    SteamAgeRevolution.instance.getLogger().devInfo("Generating ore at: " + startPos.toString());
                }
            }
        }
    }

    public static class OreEntry {
        final String config;
        final int veinSize;
        final int rarity;
        final int maxY;
        final IBlockState ore;

        public OreEntry(String config, int rarity, int maxY, int veinSize, IBlockState ore) {
            this.config = config;
            this.rarity = rarity;
            this.maxY = maxY;
            this.veinSize = veinSize;
            this.ore = ore;
        }
    }

}
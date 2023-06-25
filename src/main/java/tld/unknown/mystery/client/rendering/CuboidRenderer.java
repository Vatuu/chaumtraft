package tld.unknown.mystery.client.rendering;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class CuboidRenderer {

    private TextureAtlasSprite atlasSprite;

    private int texSizeWidth, textSizeHeight;

    private float xMinU, xMinV, xMaxU, xMaxV;
    private float yMinU, yMinV, yMaxU, yMaxV;
    private float zMinU, zMinV, zMaxU, zMaxV;

    private Vector3f bfl, bfr, bbl, bbr;
    private Vector3f tfl, tfr, tbl, tbr;

    public void draw(VertexConsumer consumer, Matrix4f modelMatrix, int colour, boolean applyLight, int light) {
        RenderHelper.drawFace(Direction.NORTH, consumer, modelMatrix, bfl, tfr, colour, getMinU(Direction.NORTH), getMinV(Direction.NORTH), getMaxU(Direction.NORTH), getMaxV(Direction.NORTH), applyLight, light);
        RenderHelper.drawFace(Direction.SOUTH, consumer, modelMatrix, bbl, tbr, colour, getMinU(Direction.SOUTH), getMinV(Direction.SOUTH), getMaxU(Direction.SOUTH), getMaxV(Direction.SOUTH), applyLight, light);
        RenderHelper.drawFace(Direction.EAST, consumer, modelMatrix, bfr, tbr, colour, getMinU(Direction.EAST), getMinV(Direction.EAST), getMaxU(Direction.EAST), getMaxV(Direction.EAST), applyLight, light);
        RenderHelper.drawFace(Direction.WEST, consumer, modelMatrix, bfl, tbl, colour, getMinU(Direction.WEST), getMinV(Direction.WEST), getMaxU(Direction.WEST), getMaxV(Direction.WEST), applyLight, light);
        RenderHelper.drawFace(Direction.UP, consumer, modelMatrix, tfl, tbr, colour, getMinU(Direction.UP), getMinV(Direction.UP), getMaxU(Direction.UP), getMaxV(Direction.UP), applyLight, light);
        RenderHelper.drawFace(Direction.DOWN, consumer, modelMatrix, bfl, bbr, colour, getMinU(Direction.DOWN), getMinV(Direction.DOWN), getMaxU(Direction.DOWN), getMaxV(Direction.DOWN), applyLight, light);
    }

    public CuboidRenderer prepare(float width, float height, float depth, int textureWidth, int textureHeight) {
        prepare(width, height, depth, textureWidth, textureHeight, null);
        return this;
    }


    public CuboidRenderer prepare(float width, float height, float depth, int textureWidth, int textureHeight, TextureAtlasSprite sprite) {
        this.atlasSprite = sprite;

        this.texSizeWidth = textureWidth;
        this.textSizeHeight = textureHeight;

        this.xMinU = this.yMinU = this.zMinU = this.xMinV = this.yMinV = this.zMinV = 0;
        this.xMaxU = this.yMaxU = this.zMaxU = textureWidth;
        this.xMaxV = this.yMaxV = this.zMaxV = textureHeight;

        this.bfl = new Vector3f(0, 0, 0);
        this.bfr = new Vector3f(width, 0, 0);
        this.bbl = new Vector3f(0, 0, depth);
        this.bbr = new Vector3f(width, 0, depth);
        this.tfl = new Vector3f(0, height, 0);
        this.tfr = new Vector3f(width, height, 0);
        this.tbl = new Vector3f(0, height, depth);
        this.tbr = new Vector3f(width, height, depth);

        return this;
    }

    public CuboidRenderer setUVs(Direction.Axis axis, float minU, float minV, float maxU, float maxV) {
        switch(axis) {
            case X -> {
                this.xMinU = minU;
                this.xMinV = minV;
                this.xMaxU = maxU;
                this.xMaxV = maxV;
            }
            case Y -> {
                this.yMinU = minU;
                this.yMinV = minV;
                this.yMaxU = maxU;
                this.yMaxV = maxV;
            }
            case Z -> {
                this.zMinU = minU;
                this.zMinV = minV;
                this.zMaxU = maxU;
                this.zMaxV = maxV;
            }
        }

        return this;
    }

    private float getMinU(Direction dir) {
        return switch(dir) {
            case UP, DOWN -> texCoord(texSizeWidth, yMinU);
            case NORTH, SOUTH -> texCoord(texSizeWidth, zMinU);
            case EAST, WEST -> texCoord(texSizeWidth, xMinU);
        } + (atlasSprite != null ? atlasSprite.getU0() : 0);
    }

    private float getMinV(Direction dir) {
        return switch(dir) {
            case UP, DOWN -> texCoord(textSizeHeight, yMinV);
            case NORTH, SOUTH -> texCoord(textSizeHeight, zMinV);
            case EAST, WEST -> texCoord(textSizeHeight, xMinV);
        } + (atlasSprite != null ? atlasSprite.getV0() : 0);
    }

    private float getMaxU(Direction dir) {
        return switch(dir) {
            case UP, DOWN -> texCoord(texSizeWidth, yMaxU);
            case NORTH, SOUTH -> texCoord(texSizeWidth, zMaxU);
            case EAST, WEST -> texCoord(texSizeWidth, xMaxU);
        } + (atlasSprite != null ? atlasSprite.getU1() : 0);
    }

    private float getMaxV(Direction dir) {
        return switch(dir) {
            case UP, DOWN -> texCoord(textSizeHeight, yMaxV);
            case NORTH, SOUTH -> texCoord(textSizeHeight, zMaxV);
            case EAST, WEST -> texCoord(textSizeHeight, xMaxV);
        } + (atlasSprite != null ? atlasSprite.getV1() : 0);
    }

    private float texCoord(int size, float value) {
        return 1F / size * value;
    }
}
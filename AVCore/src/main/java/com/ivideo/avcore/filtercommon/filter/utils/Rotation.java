package com.ivideo.avcore.filtercommon.filter.utils;

/**
 * @author hahajing 企鹅：444511958
 * @version 1.0.0
 * @createDate 2022/6/16 15:23
 * @description
 * @updateUser hahajing
 * @updateDate 2022/6/16 15:23
 * @updateRemark
 */
public enum Rotation {
    NORMAL, ROTATION_90, ROTATION_180, ROTATION_270;

    /**
     * Retrieves the int representation of the Rotation.
     *
     * @return 0, 90, 180 or 270
     */
    public int asInt() {
        switch (this) {
            case NORMAL: return 0;
            case ROTATION_90: return 90;
            case ROTATION_180: return 180;
            case ROTATION_270: return 270;
            default: throw new IllegalStateException("Unknown Rotation!");
        }
    }

    /**
     * Create a Rotation from an integer. Needs to be either 0, 90, 180 or 270.
     *
     * @param rotation 0, 90, 180 or 270
     * @return Rotation object
     */
    public static Rotation fromInt(int rotation) {
        switch (rotation) {
            case 0: return NORMAL;
            case 90: return ROTATION_90;
            case 180: return ROTATION_180;
            case 270: return ROTATION_270;
            case 360: return NORMAL;
            default: throw new IllegalStateException(
                    rotation + " is an unknown rotation. Needs to be either 0, 90, 180 or 270!");
        }
    }
}
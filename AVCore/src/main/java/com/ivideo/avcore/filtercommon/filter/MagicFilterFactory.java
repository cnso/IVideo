package com.ivideo.avcore.filtercommon.filter;

import com.ivideo.avcore.filtercommon.filter.magicfilter.MagicAntiqueFilter;

/**
 * @author hahajing 企鹅：444511958
 * @version 1.0.0
 * @createDate 2022/6/16 15:27
 * @description
 * @updateUser hahajing
 * @updateDate 2022/6/16 15:27
 * @updateRemark
 */
public class MagicFilterFactory {

    private static MagicFilterType filterType = MagicFilterType.NONE;

    public static GPUImageFilter initFilters(MagicFilterType type) {
        if (type == null) {
            return null;
        }
        filterType = type;
        switch (type) {
            case ANTIQUE:
                return new MagicAntiqueFilter();
//            case BRANNAN:
//                return new MagicBrannanFilter();
//            case FREUD:
//                return new MagicFreudFilter();
//            case HEFE:
//                return new MagicHefeFilter();
//            case HUDSON:
//                return new MagicHudsonFilter();
//            case INKWELL:
//                return new MagicInkwellFilter();
//            case N1977:
//                return new MagicN1977Filter();
//            case NASHVILLE:
//                return new MagicNashvilleFilter();
//            case COOL:
//                return new MagicCoolFilter();
            case WARM:
                return new MagicWarmFilter();
            default:
                return null;
        }
    }

    public MagicFilterType getCurrentFilterType() {
        return filterType;
    }

    private static class MagicWarmFilter extends GPUImageFilter {
    }
}

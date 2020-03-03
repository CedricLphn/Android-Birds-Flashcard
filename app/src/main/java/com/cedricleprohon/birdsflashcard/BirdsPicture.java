package com.cedricleprohon.birdsflashcard;

public enum BirdsPicture {
    ACCENTEUR_MOUCHET("accenteur_mouchet.jpg", R.drawable.accenteur_mouchet),
    ALOUETE("alouette.jpg", R.drawable.alouette),
    BRUANT("bruant.jpg", R.drawable.bruant),
    COUCOU("coucou.jpg", R.drawable.coucou),
    ETOURNEAU("etourneau.jpg", R.drawable.etourneau),
    FAUVETTE("fauvette.jpg", R.drawable.fauvette);

    private final String fileName;
    private final int resId;

    BirdsPicture(String fileName, int resId){
        this.fileName = fileName;
        this.resId = resId;
    }

    /**
     *
     * @param imageFileName
     * @return -1 if image not found
     */
    public static int getResId(String imageFileName){
        int resId = -1;
        for (BirdsPicture myImage : BirdsPicture.values()) {
            if (imageFileName.equals(myImage.fileName)){
                resId = myImage.resId;
                break;
            }
        }
        return resId;
    }
}

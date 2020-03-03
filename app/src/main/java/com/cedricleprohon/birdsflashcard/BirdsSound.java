package com.cedricleprohon.birdsflashcard;

public enum BirdsSound {
    ACCENTEUR_MOUCHET("accenteurmouchet.mp3", R.raw.accenteurmouchet),
    ALOUETE("Alouette_des_champs.mp3", R.raw.alouette_des_champs),
    BRUANT("bruantjaune.mp3", R.raw.bruantjaune),
    COUCOU("coucou.mp3", R.raw.coucou),
    ETOURNEAU("etourneau.mp3", R.raw.etourneau),
    FAUVETTE("fauvettetetenoire.mp3", R.raw.fauvettetetenoire);

    private final String fileName;
    private final int resId;

    BirdsSound(String fileName, int resId){
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
        for (BirdsSound sound : BirdsSound.values()) {
            if (imageFileName.equals(sound.fileName)){
                resId = sound.resId;
                break;
            }
        }
        return resId;
    }
}

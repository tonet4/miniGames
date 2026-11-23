package com.antonio.minigames.presentation.syllables

import androidx.annotation.DrawableRes
import com.antonio.minigames.R
import com.antonio.minigames.domain.syllables.entity.Word

@DrawableRes
fun Word.toPhotoRes(): Int = when (this) {
    Word.GATO -> R.drawable.photo_gato
    Word.PATO -> R.drawable.photo_pato
    Word.MONO -> R.drawable.photo_mono
    Word.LORO -> R.drawable.photo_loro
    Word.RANA -> R.drawable.photo_rana
    Word.OSO -> R.drawable.photo_oso
    Word.LOBO -> R.drawable.photo_lobo
    Word.FOCA -> R.drawable.photo_foca
    Word.ROSA -> R.drawable.photo_rosa
    Word.MOTO -> R.drawable.photo_moto
    Word.CASA -> R.drawable.photo_casa
    Word.CAMA -> R.drawable.photo_cama
    Word.LUNA -> R.drawable.photo_luna
    Word.PALOMA -> R.drawable.photo_paloma
    Word.PELOTA -> R.drawable.photo_pelota
}

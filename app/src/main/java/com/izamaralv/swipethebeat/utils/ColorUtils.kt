package com.izamaralv.swipethebeat.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.izamaralv.swipethebeat.common.backgroundColor
import com.izamaralv.swipethebeat.common.colorName
import com.izamaralv.swipethebeat.common.contentColor
import com.izamaralv.swipethebeat.common.darkComponentColor
import com.izamaralv.swipethebeat.common.lightComponentColor
import com.izamaralv.swipethebeat.ui.theme.BlueBackground
import com.izamaralv.swipethebeat.ui.theme.BlueContent
import com.izamaralv.swipethebeat.ui.theme.BlueDarkComponent
import com.izamaralv.swipethebeat.ui.theme.BlueLightComponent
import com.izamaralv.swipethebeat.ui.theme.BlueName
import com.izamaralv.swipethebeat.ui.theme.DarkBackground
import com.izamaralv.swipethebeat.ui.theme.DarkContent
import com.izamaralv.swipethebeat.ui.theme.DarkDarkComponent
import com.izamaralv.swipethebeat.ui.theme.DarkLightComponent
import com.izamaralv.swipethebeat.ui.theme.DarkName
import com.izamaralv.swipethebeat.ui.theme.GreenBackground
import com.izamaralv.swipethebeat.ui.theme.GreenContent
import com.izamaralv.swipethebeat.ui.theme.GreenDarkComponent
import com.izamaralv.swipethebeat.ui.theme.GreenLightComponent
import com.izamaralv.swipethebeat.ui.theme.GreenName
import com.izamaralv.swipethebeat.ui.theme.HighContrastBackground
import com.izamaralv.swipethebeat.ui.theme.HighContrastContent
import com.izamaralv.swipethebeat.ui.theme.HighContrastDarkComponent
import com.izamaralv.swipethebeat.ui.theme.HighContrastLightComponent
import com.izamaralv.swipethebeat.ui.theme.HighContrastName
import com.izamaralv.swipethebeat.ui.theme.LightBackground
import com.izamaralv.swipethebeat.ui.theme.LightContent
import com.izamaralv.swipethebeat.ui.theme.LightDarkComponent
import com.izamaralv.swipethebeat.ui.theme.LightLightComponent
import com.izamaralv.swipethebeat.ui.theme.LightName
import com.izamaralv.swipethebeat.ui.theme.OrangeBackground
import com.izamaralv.swipethebeat.ui.theme.OrangeContent
import com.izamaralv.swipethebeat.ui.theme.OrangeDarkComponent
import com.izamaralv.swipethebeat.ui.theme.OrangeLightComponent
import com.izamaralv.swipethebeat.ui.theme.OrangeName
import com.izamaralv.swipethebeat.ui.theme.PurpleBackground
import com.izamaralv.swipethebeat.ui.theme.PurpleContent
import com.izamaralv.swipethebeat.ui.theme.PurpleDarkComponent
import com.izamaralv.swipethebeat.ui.theme.PurpleLightComponent
import com.izamaralv.swipethebeat.ui.theme.PurpleName
import com.izamaralv.swipethebeat.ui.theme.RedBackground
import com.izamaralv.swipethebeat.ui.theme.RedContent
import com.izamaralv.swipethebeat.ui.theme.RedDarkComponent
import com.izamaralv.swipethebeat.ui.theme.RedLightComponent
import com.izamaralv.swipethebeat.ui.theme.RedName

fun changeTheme(theme: String) {
    /* TODO: change theme in database and from database */
    // colors
    var backgroundColor by backgroundColor
    var darkComponentColor by darkComponentColor
    var lightComponentColor by lightComponentColor
    var contentColor by contentColor
    var colorName by colorName

    when (theme) {
        GreenName -> {
            backgroundColor = GreenBackground
            darkComponentColor = GreenDarkComponent
            lightComponentColor = GreenLightComponent
            contentColor = GreenContent
            colorName = GreenName
        }

        BlueName -> {
            backgroundColor = BlueBackground
            darkComponentColor = BlueDarkComponent
            lightComponentColor = BlueLightComponent
            contentColor = BlueContent
            colorName = BlueName
        }

        OrangeName -> {
            backgroundColor = OrangeBackground
            darkComponentColor = OrangeDarkComponent
            lightComponentColor = OrangeLightComponent
            contentColor = OrangeContent
            colorName = OrangeName
        }

        RedName -> {
            backgroundColor = RedBackground
            darkComponentColor = RedDarkComponent
            lightComponentColor = RedLightComponent
            contentColor = RedContent
            colorName = RedName
        }

        PurpleName -> {
            backgroundColor = PurpleBackground
            darkComponentColor = PurpleDarkComponent
            lightComponentColor = PurpleLightComponent
            contentColor = PurpleContent
            colorName = PurpleName
        }

        LightName -> {
            backgroundColor = LightBackground
            darkComponentColor = LightDarkComponent
            lightComponentColor = LightLightComponent
            contentColor = LightContent
            colorName = LightName
        }

        DarkName -> {
            backgroundColor = DarkBackground
            darkComponentColor = DarkDarkComponent
            lightComponentColor = DarkLightComponent
            contentColor = DarkContent
            colorName = DarkName
        }



        HighContrastName -> {
            backgroundColor = HighContrastBackground
            darkComponentColor = HighContrastDarkComponent
            lightComponentColor = HighContrastLightComponent
            contentColor = HighContrastContent
            colorName = HighContrastName
        }

    }
}
package org.jash.homepage.viewmodel

import org.jash.mvicore.viewmodel.IIntent

sealed class HomepageIntent:IIntent {
    object LoadType:HomepageIntent()
}
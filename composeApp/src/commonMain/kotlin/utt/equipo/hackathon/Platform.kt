package utt.equipo.hackathon

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
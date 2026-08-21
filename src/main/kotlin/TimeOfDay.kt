data class TimeOfDay (val hour: Int, val minute: Int) : Comparable<TimeOfDay> {

    override fun compareTo(other: TimeOfDay): Int {
        return when {
            this.hour != other.hour -> this.hour.compareTo(other.hour)
            else -> this.minute.compareTo(other.minute)
        }
    }
}
data class CounterOfTrainings(val coach: Coach,
                              val countTrainingSession: Int) : Comparable<CounterOfTrainings> {

    override fun compareTo(other: CounterOfTrainings): Int {
        return other.countTrainingSession.compareTo(this.countTrainingSession)
    }
}
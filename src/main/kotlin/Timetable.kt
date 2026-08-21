import java.time.DayOfWeek
import java.util.*
import java.util.Map

class Timetable {
    val timetable: MutableMap<DayOfWeek, TreeMap<TimeOfDay, MutableList<TrainingSession>>> = mutableMapOf()

    fun addNewTrainingSession(trainingSession: TrainingSession) {
        val timeMap = timetable.getOrPut(trainingSession.dayOfWeek) { TreeMap() }
        val sessions = timeMap.getOrPut(trainingSession.timeOfDay) { mutableListOf() }

        sessions.add(trainingSession)
    }

    fun getTrainingSessionsForDay(dayOfWeek: DayOfWeek) : TreeMap<TimeOfDay, MutableList<TrainingSession>>? {
        val sessions = timetable.get(dayOfWeek)
        if (sessions == null) {
            println("Тренировок нет")
            return null
        } else {
            return sessions
        }
    }

    fun getTrainingSessionsForDayAndTime(dayOfWeek: DayOfWeek, timeOfDay: TimeOfDay) : List<TrainingSession>? {
        val sessionsDayOfWeek: TreeMap<TimeOfDay, MutableList<TrainingSession>>? = timetable.get(dayOfWeek)
        val sessions: MutableList<TrainingSession>? = sessionsDayOfWeek?.get(timeOfDay)

        if (sessions == null) {
            println("Тренировок нет")
            return null
        } else {
            return sessions
        }
    }

    fun getCountByCoaches(coach: Coach) : List<CounterOfTrainings> {
        val countsTrainings: MutableMap<Coach, Int> = mutableMapOf()

        for (dayTimeTable in timetable.values) {
            for (sessions in dayTimeTable.values) {
                for (session in sessions) {
                    val coach = session.coach

                    countsTrainings.put(coach, countsTrainings.getOrDefault(coach, 0) + 1)
                }
            }
        }

        val countOfTrainings: MutableList<CounterOfTrainings> = mutableListOf()
        for (entry in countsTrainings.entries) {
            val coach = entry.key
            val count = entry.value

            val counter = CounterOfTrainings(coach, count)
            countOfTrainings.add(counter)
        }

        countOfTrainings.sort()
        return countOfTrainings
    }
}
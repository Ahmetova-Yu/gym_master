import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import Timetable
import java.time.DayOfWeek

class TimetableTest {
    private lateinit var timetable: Timetable

    @BeforeEach
    fun setUp() {
        timetable = Timetable()
    }

    @Test
    fun `get session for day single session`() {
        val group = Group("Акробатика дл детей", Age.CHILD, 60)
        val coach = Coach("Васильев", "Николай", "Сергеевич")
        val singleTrainingSession = TrainingSession(group, coach, DayOfWeek.MONDAY, TimeOfDay(13, 0))

        timetable.addNewTrainingSession(singleTrainingSession)

        val mondaySession = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY)
        val thursdaySession = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY)

        assertEquals(1, mondaySession?.size)
        assertEquals(null, thursdaySession?.size)
    }

    @Test
    fun `get session for day multyple sessions`() {
        val coach = Coach("Васильев", "Николай", "Сергеевич")
        val groupAdult = Group("Акробатика для взрослых", Age.ADULT, 90)
        val thursdayAdultTrainingSession = TrainingSession(groupAdult, coach, DayOfWeek.THURSDAY, TimeOfDay(20, 0))

        timetable.addNewTrainingSession(thursdayAdultTrainingSession)

        val groupChild = Group("Акробатика для детей", Age.CHILD, 60)

        val mondayChildTrainingSession = TrainingSession(groupChild, coach, DayOfWeek.MONDAY, TimeOfDay(13, 0))
        val thursdayChildTrainingSession = TrainingSession(groupChild, coach, DayOfWeek.THURSDAY, TimeOfDay(13, 0))
        val saturdayChildTrainingSession = TrainingSession(groupChild, coach, DayOfWeek.SATURDAY, TimeOfDay(10, 0))

        timetable.addNewTrainingSession(mondayChildTrainingSession)
        timetable.addNewTrainingSession(thursdayChildTrainingSession)
        timetable.addNewTrainingSession(saturdayChildTrainingSession)

        val mondaySession = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY)
        assertEquals(1, mondaySession?.size)

        val thursdaySession = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY)
        assertEquals(2, thursdaySession?.size)

        val rightTime = if (thursdaySession != null) {
            ArrayList(thursdaySession.keys)
        } else {
            mutableListOf()
        }

        assertEquals(TimeOfDay(13, 0), rightTime[0])
        assertEquals(TimeOfDay(20, 0), rightTime[1])

        val wednesdaySession = timetable.getTrainingSessionsForDay(DayOfWeek.WEDNESDAY)
        assertEquals(null, wednesdaySession?.size)
    }

    @Test
    fun `get sessions for day and time`() {
        val groupChild = Group("Акробатика для детей", Age.CHILD, 60)
        val coach = Coach("Васильев", "Николай", "Сергеевич")
        val thursdayAdultTrainingSession = TrainingSession(groupChild, coach, DayOfWeek.MONDAY, TimeOfDay(12, 0))

        timetable.addNewTrainingSession(thursdayAdultTrainingSession)

        val mondaySession = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, TimeOfDay(12, 0))
        val thursdaySession = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, TimeOfDay(13, 0))

        assertEquals(1, mondaySession?.size)
        assertEquals(null, thursdaySession?.size)
    }

    @Test
    fun `check add coach in trainings on same time`() {
        val coach = Coach("Васильев", "Николай", "Сергеевич")
        val group1 = Group("Group1", Age.ADULT, 60)
        val group2 = Group("Group2", Age.ADULT, 60)

        val sessions1 = TrainingSession(group1, coach, DayOfWeek.MONDAY, TimeOfDay(10, 0))
        val sessions2 = TrainingSession(group2, coach, DayOfWeek.MONDAY, TimeOfDay(10, 0))

        timetable.addNewTrainingSession(sessions1)
        timetable.addNewTrainingSession(sessions2)

        val sessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, TimeOfDay(10, 0))
        assertEquals(2, sessions?.size)

        val dayTimeTable = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY)
        assertEquals(1, dayTimeTable?.size)
    }

    @Test
    fun `check add coach in trainings on other time`() {
        val coach = Coach("Васильев", "Николай", "Сергеевич")
        val group1 = Group("Group1", Age.ADULT, 60)
        val group2 = Group("Group2", Age.ADULT, 60)

        val sessions1 = TrainingSession(group1, coach, DayOfWeek.MONDAY, TimeOfDay(10, 0))
        val sessions2 = TrainingSession(group2, coach, DayOfWeek.MONDAY, TimeOfDay(15, 0))

        timetable.addNewTrainingSession(sessions1)
        timetable.addNewTrainingSession(sessions2)

        val dayTimeTable = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY)
        assertEquals(2, dayTimeTable?.size)
    }

    @Test
    fun `check add group on trainings group on same time`() {
        val coach1 = Coach("Васильев", "Николай", "Сергеевич")
        val coach2 = Coach("Васильев", "Николай", "Сергеевич")
        val group = Group("Group", Age.ADULT, 60)

        val sessions1 = TrainingSession(group, coach1, DayOfWeek.MONDAY, TimeOfDay(10, 0))
        val sessions2 = TrainingSession(group, coach2, DayOfWeek.MONDAY, TimeOfDay(10, 0))

        timetable.addNewTrainingSession(sessions1)
        timetable.addNewTrainingSession(sessions2)

        val sessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, TimeOfDay(10, 0))
        assertEquals(2, sessions?.size)

        val dayTimeTable = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY)
        assertEquals(1, dayTimeTable?.size)
    }

    @Test
    fun `check add group on trainings group on other time`() {
        val coach = Coach("Васильев", "Николай", "Сергеевич")
        val group = Group("Group", Age.ADULT, 60)

        val sessions1 = TrainingSession(group, coach, DayOfWeek.MONDAY, TimeOfDay(10, 0))
        val sessions2 = TrainingSession(group, coach, DayOfWeek.MONDAY, TimeOfDay(11, 0))

        timetable.addNewTrainingSession(sessions1)
        timetable.addNewTrainingSession(sessions2)

        val sessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, TimeOfDay(10, 0))
        assertEquals(1, sessions?.size)

        val dayTimeTable = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY)
        assertEquals(2, dayTimeTable?.size)
    }
}
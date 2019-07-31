package info.nightscout.androidaps.database.transactions.combo

import info.nightscout.androidaps.database.embedments.InterfaceIDs
import info.nightscout.androidaps.database.entities.TemporaryBasal
import info.nightscout.androidaps.database.transactions.Transaction
import java.util.*

class ComboInsertTemporaryBasalTransaction(
        val timestamp: Long,
        val duration: Long,
        val absolute: Boolean,
        val rate: Double,
        val pumpSerial: String
) : Transaction<Unit>() {

    override fun run() {
        database.temporaryBasalDao.insertNewEntry(TemporaryBasal(
                timestamp = timestamp,
                utcOffset = TimeZone.getDefault().getOffset(timestamp).toLong(),
                type = TemporaryBasal.Type.NORMAL,
                absolute = false,
                rate = rate,
                duration = duration
        ).apply {
            interfaceIDs.pumpSerial = pumpSerial
            interfaceIDs.startId = it.first.eventId
            interfaceIDs.endId = it.second?.eventId
            interfaceIDs.pumpType = InterfaceIDs.PumpType.ACCU_CHEK_INSIGHT
            changes.add(this)
        })
    }
}
package com.mw.rfniias.spreadexchange.aspreadclient

import android.os.Parcel
import android.os.Parcelable

data class AEvent(
    val event: Event = Event.NO_MESSAGE,
    val group: String = "",
    val number: Int = 0,
    val value: Long = 0,
    val valueAdditional: Long = 0,
    val str: ByteArray = ByteArray(0),
    val error: Int = 0,
    val time: Long = 0
) : Parcelable {

    constructor(parcel: Parcel) : this(
        Event.valueOf(parcel.readString() ?: "NO_MESSAGE"),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.createByteArray() ?: ByteArray(0),
        parcel.readInt(),
        parcel.readLong())


    enum class Event {
        //        UNKNOWN_MESSAGE,                        ///< Неизвестное сообщение
        NO_MESSAGE,                             ///< Неизвестное сообщение

        ALL_DATA,                               ///< Запрос опорного кадра.
        GAC_SP_TIME,                            ///< Текущее время
        GAC_SP_STR_UPR_TRANSL,                  ///< Данные об отцепе на стрелке
        GAC_SP_ZMD_TRANSL,                      ///< Данные об отцепе на замедлителе
        GAC_SP_DSPG_PROGR_ROSP,                 ///< Кадр терминала - сортировочный лист
        GAC_SP_DSPG_GPROS,                      ///< Кадр терминала - сортировочный лист повагонно
        GAC_SP_DSPG_KOL_STR_PROGR_ROSP,         ///< Количество строк в программе роспуска
        GAC_SP_DSPG_POEZD,                      ///< Номер поезда
        GAC_SP_DSPG_ROSP_REGIM,                 ///< Режим роспуска
        GAC_SP_DSPG_ROSP_GAC,                   ///< Флаг роспуска
        GAC_SP_DSPG_ROSP,                       ///< Флаг роспуска для ИУ
        GAC_SP_DSPG_NOMER_IU,                   ///< Номер текущего ИУ
        GAC_SP_IU_ZAN,                          ///< Флаг состояния ИУ (свободен/занят)
        GAC_SP_SL_PARK_PUT,                     ///< Парк Номер ааааа
        GAC_SP_NADVIG,                          ///< Надвиг ДСПГ
        GAC_SP_DSPG_PROGR_ROSP_FULL,            ///< Кадр терминала - сортировочный лист включая сошедшие

        GAC_KGM_SVET,                           ///< Состояние горочных светофоров
        GAC_KGM_TR_IU,                          ///< Системный номер отцепа, транслированного на измерительный участок
        GAC_KGM_NAD_SPEED,                      ///< Фактическая скорость надвига

        ARS_KGM_CALC_VNAD                       ///< Рекомендованная скорость и светофор
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(event.toString())
        parcel.writeString(group)
        parcel.writeInt(number)
        parcel.writeLong(value)
        parcel.writeLong(valueAdditional)
        parcel.writeByteArray(str)
        parcel.writeInt(error)
        parcel.writeLong(time)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AEvent> {
        override fun createFromParcel(parcel: Parcel): AEvent {
            return AEvent(parcel)
        }

        override fun newArray(size: Int): Array<AEvent?> {
            return arrayOfNulls(size)
        }
    }

    interface HandlerAEvent {
        fun processingAEvent(aEvent: AEvent)
    }
}
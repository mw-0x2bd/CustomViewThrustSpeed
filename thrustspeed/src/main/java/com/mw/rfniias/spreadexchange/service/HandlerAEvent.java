/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /mnt/work/project/android/SpreadExchange/spreadexchange/src/main/aidl/com/mw/rfniias/spreadexchange/service/HandlerAEvent.aidl
 */
package com.mw.rfniias.spreadexchange.service;
public interface HandlerAEvent extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mw.rfniias.spreadexchange.service.HandlerAEvent
{
private static final java.lang.String DESCRIPTOR = "com.mw.rfniias.spreadexchange.service.HandlerAEvent";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mw.rfniias.spreadexchange.service.HandlerAEvent interface,
 * generating a proxy if needed.
 */
public static com.mw.rfniias.spreadexchange.service.HandlerAEvent asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mw.rfniias.spreadexchange.service.HandlerAEvent))) {
return ((com.mw.rfniias.spreadexchange.service.HandlerAEvent)iin);
}
return new com.mw.rfniias.spreadexchange.service.HandlerAEvent.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
java.lang.String descriptor = DESCRIPTOR;
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(descriptor);
return true;
}
case TRANSACTION_processing:
{
data.enforceInterface(descriptor);
com.mw.rfniias.spreadexchange.aspreadclient.AEvent _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mw.rfniias.spreadexchange.aspreadclient.AEvent.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.processing(_arg0);
reply.writeNoException();
return true;
}
default:
{
return super.onTransact(code, data, reply, flags);
}
}
}
private static class Proxy implements com.mw.rfniias.spreadexchange.service.HandlerAEvent
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void processing(com.mw.rfniias.spreadexchange.aspreadclient.AEvent aEvent) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((aEvent!=null)) {
_data.writeInt(1);
aEvent.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_processing, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_processing = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public void processing(com.mw.rfniias.spreadexchange.aspreadclient.AEvent aEvent) throws android.os.RemoteException;
}

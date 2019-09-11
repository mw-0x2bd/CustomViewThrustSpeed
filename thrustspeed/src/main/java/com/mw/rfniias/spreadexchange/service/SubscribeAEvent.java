/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /mnt/work/project/android/SpreadExchange/spreadexchange/src/main/aidl/com/mw/rfniias/spreadexchange/service/SubscribeAEvent.aidl
 */
package com.mw.rfniias.spreadexchange.service;
public interface SubscribeAEvent extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mw.rfniias.spreadexchange.service.SubscribeAEvent
{
private static final java.lang.String DESCRIPTOR = "com.mw.rfniias.spreadexchange.service.SubscribeAEvent";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mw.rfniias.spreadexchange.service.SubscribeAEvent interface,
 * generating a proxy if needed.
 */
public static com.mw.rfniias.spreadexchange.service.SubscribeAEvent asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mw.rfniias.spreadexchange.service.SubscribeAEvent))) {
return ((com.mw.rfniias.spreadexchange.service.SubscribeAEvent)iin);
}
return new com.mw.rfniias.spreadexchange.service.SubscribeAEvent.Stub.Proxy(obj);
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
case TRANSACTION_registerHandler:
{
data.enforceInterface(descriptor);
com.mw.rfniias.spreadexchange.service.HandlerAEvent _arg0;
_arg0 = com.mw.rfniias.spreadexchange.service.HandlerAEvent.Stub.asInterface(data.readStrongBinder());
this.registerHandler(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_unregisterHandler:
{
data.enforceInterface(descriptor);
com.mw.rfniias.spreadexchange.service.HandlerAEvent _arg0;
_arg0 = com.mw.rfniias.spreadexchange.service.HandlerAEvent.Stub.asInterface(data.readStrongBinder());
this.unregisterHandler(_arg0);
reply.writeNoException();
return true;
}
default:
{
return super.onTransact(code, data, reply, flags);
}
}
}
private static class Proxy implements com.mw.rfniias.spreadexchange.service.SubscribeAEvent
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
@Override public void registerHandler(com.mw.rfniias.spreadexchange.service.HandlerAEvent handlerAEvent) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((handlerAEvent!=null))?(handlerAEvent.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registerHandler, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void unregisterHandler(com.mw.rfniias.spreadexchange.service.HandlerAEvent handlerAEvent) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((handlerAEvent!=null))?(handlerAEvent.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_unregisterHandler, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_registerHandler = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_unregisterHandler = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
public void registerHandler(com.mw.rfniias.spreadexchange.service.HandlerAEvent handlerAEvent) throws android.os.RemoteException;
public void unregisterHandler(com.mw.rfniias.spreadexchange.service.HandlerAEvent handlerAEvent) throws android.os.RemoteException;
}

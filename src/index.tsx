import { NativeModules } from 'react-native';

type AesGcmModifiedType = {
  multiply(a: number, b: number): Promise<number>;
};

const { AesGcmModified } = NativeModules;

export default AesGcmModified as AesGcmModifiedType;

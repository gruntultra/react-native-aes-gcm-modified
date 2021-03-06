import { NativeModules } from 'react-native';

type AesGcmModifiedType = {
  decrypt(
    base64Ciphertext: string,
    key: string,
  ): Promise<string>;
  encrypt(
    plainText: string,
    inBinary: boolean,
    key: string
  ): Promise<string>;
};

const { AesGcmModified } = NativeModules;

export default AesGcmModified as AesGcmModifiedType;

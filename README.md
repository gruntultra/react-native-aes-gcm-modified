# react-native-aes-gcm-modified

Enable the usage of aes gcm for your mobile application

## Installation

```sh
npm install react-native-aes-gcm-modified
```

## Usage

```js
import AesGcmModified from "react-native-aes-gcm-modified";


```
### Encrypt data

```ts
function encrypt(
  plainText: string,
  inBinary: boolean,
  key: string
): Promise<EncryptedData>;
```

### Decrypt data

```ts
function decrypt(
  base64Ciphertext: string,
  key: string,
  isBinary: boolean
): Promise<string>;
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

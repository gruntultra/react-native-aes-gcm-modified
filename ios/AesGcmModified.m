#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(AesGcmModified, NSObject)

RCT_EXTERN_METHOD(decrypt:(NSString *)base64CipherText
                    withKey:(NSString *)key
                    withResolver:(RCTPromiseResolveBlock)resolve
                    withRejecter:(RCTPromiseRejectBlock)reject)
RCT_EXTERN_METHOD(encrypt:(NSString *)plainData
                inBase64:(BOOL)inBase64
                withKey:(NSString *)key
                withResolver:(RCTPromiseResolveBlock)resolve
                withRejecter:(RCTPromiseRejectBlock)reject)

@end

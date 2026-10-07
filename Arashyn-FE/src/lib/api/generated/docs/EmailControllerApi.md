# EmailControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**sendVerificationEmail**](#sendverificationemail) | **POST** /email/send-mail | |

# **sendVerificationEmail**
> sendVerificationEmail(sendSingleMailRequest)


### Example

```typescript
import {
    EmailControllerApi,
    Configuration,
    SendSingleMailRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new EmailControllerApi(configuration);

let sendSingleMailRequest: SendSingleMailRequest; //

const { status, data } = await apiInstance.sendVerificationEmail(
    sendSingleMailRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **sendSingleMailRequest** | **SendSingleMailRequest**|  | |


### Return type

void (empty response body)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


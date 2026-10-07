# AdminPingControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**ping1**](#ping1) | **GET** /admin/ping | |

# **ping1**
> string ping1()


### Example

```typescript
import {
    AdminPingControllerApi,
    Configuration
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new AdminPingControllerApi(configuration);

const { status, data } = await apiInstance.ping1();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**string**

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


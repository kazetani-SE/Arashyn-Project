# PingControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**me**](#me) | **GET** /ping/me | |
|[**ping**](#ping) | **GET** /ping | |

# **me**
> string me()


### Example

```typescript
import {
    PingControllerApi,
    Configuration
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new PingControllerApi(configuration);

const { status, data } = await apiInstance.me();
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

# **ping**
> string ping()


### Example

```typescript
import {
    PingControllerApi,
    Configuration
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new PingControllerApi(configuration);

const { status, data } = await apiInstance.ping();
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


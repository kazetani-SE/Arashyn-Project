# ExportControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**exportCsvTemplate**](#exportcsvtemplate) | **POST** /export/csv | |
|[**exportTextTemplate**](#exporttexttemplate) | **POST** /export/text | |

# **exportCsvTemplate**
> string exportCsvTemplate()


### Example

```typescript
import {
    ExportControllerApi,
    Configuration,
    TemplateExportRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new ExportControllerApi(configuration);

let request: TemplateExportRequest; // (default to undefined)

const { status, data } = await apiInstance.exportCsvTemplate(
    request
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **request** | **TemplateExportRequest** |  | defaults to undefined|


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

# **exportTextTemplate**
> string exportTextTemplate()


### Example

```typescript
import {
    ExportControllerApi,
    Configuration,
    TemplateExportRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new ExportControllerApi(configuration);

let request: TemplateExportRequest; // (default to undefined)

const { status, data } = await apiInstance.exportTextTemplate(
    request
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **request** | **TemplateExportRequest** |  | defaults to undefined|


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


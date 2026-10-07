# UserGrammarSummarisedResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **string** |  | [optional] [default to undefined]
**title** | **string** |  | [optional] [default to undefined]
**components** | [**Array&lt;GrammarComponentSummaryResponse&gt;**](GrammarComponentSummaryResponse.md) |  | [optional] [default to undefined]
**meanings** | [**Array&lt;GrammarMeaningSummaryResponse&gt;**](GrammarMeaningSummaryResponse.md) |  | [optional] [default to undefined]
**filters** | [**Array&lt;GrammarFilterResponse&gt;**](GrammarFilterResponse.md) |  | [optional] [default to undefined]
**proficiency** | **string** |  | [optional] [default to undefined]
**lastReviewAt** | **string** |  | [optional] [default to undefined]

## Example

```typescript
import { UserGrammarSummarisedResponse } from 'arashyn-api';

const instance: UserGrammarSummarisedResponse = {
    id,
    title,
    components,
    meanings,
    filters,
    proficiency,
    lastReviewAt,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)

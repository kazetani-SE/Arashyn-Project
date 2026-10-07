# UserGrammarDetailResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **string** |  | [optional] [default to undefined]
**grammarId** | **string** |  | [optional] [default to undefined]
**title** | **string** |  | [optional] [default to undefined]
**language** | **string** |  | [optional] [default to undefined]
**groups** | [**Array&lt;Group&gt;**](Group.md) |  | [optional] [default to undefined]
**notes** | [**Array&lt;GrammarNoteResponse&gt;**](GrammarNoteResponse.md) |  | [optional] [default to undefined]
**filters** | [**Array&lt;GrammarFilterResponse&gt;**](GrammarFilterResponse.md) |  | [optional] [default to undefined]
**proficiency** | **string** |  | [optional] [default to undefined]
**lastReviewAt** | **string** |  | [optional] [default to undefined]

## Example

```typescript
import { UserGrammarDetailResponse } from 'arashyn-api';

const instance: UserGrammarDetailResponse = {
    id,
    grammarId,
    title,
    language,
    groups,
    notes,
    filters,
    proficiency,
    lastReviewAt,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)

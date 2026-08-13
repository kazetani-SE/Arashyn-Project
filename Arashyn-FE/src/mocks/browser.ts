import { setupWorker } from "msw/browser";
import { grammarHandler } from "@/mocks/handlers/grammar.handler.ts";

export const worker = setupWorker(
    ...grammarHandler,
);
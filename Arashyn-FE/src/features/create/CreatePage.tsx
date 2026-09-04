import { useNavigate } from "react-router-dom"
import { FeatureBackground } from "@/components/background/FeatureBackground.tsx"
import { useSetBreadcrumb } from "@/layout/topbar/hooks/useSetBreadcrumb.ts"
import { ROUTE_PATHS } from "@/app/router/route.ts"
import { Button } from "@/components/ui/button.tsx"
import { ArrowLeft } from "lucide-react"
import { GrammarCreatePart } from "@/features/create/parts/GrammarCreatePart.tsx"

export default function CreatePage() {
    const navigate = useNavigate()

    useSetBreadcrumb("Create", ROUTE_PATHS.CREATE)

    const onBack = () => navigate(ROUTE_PATHS.DISCOVER, { replace: true })

    return (
        <div>
            <FeatureBackground />
            <div className="mx-auto max-w-3xl px-6 py-10">
                <div className="mb-4">
                    <Button variant="ghost" className="gap-2" onClick={onBack}>
                        <ArrowLeft className="h-4 w-4" /> Back
                    </Button>
                </div>

                <h1 className="mb-8 text-2xl font-semibold tracking-tight text-white">Create New Grammar</h1>

                <GrammarCreatePart />
            </div>
        </div>
    )
}
export default function DiscoverPage(){
    return (
        <div>

            <DiscoverBackground/>

        </div>
    );
}

function DiscoverBackground() {
    return <div className="pointer-events-none absolute inset-0 bg-indigo-800/10" />;
}
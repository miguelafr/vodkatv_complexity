-module(vodkatv_channels).

-include_lib("eqc/include/eqc.hrl").
-include("measure.hrl").

-compile(export_all).

num_channels(N) ->
    [N + 100].

eval_cmds(N) ->
    SetupCommands = get_setup_commands(),
    RunCommands = get_run_commands(N),
    TearDownCommands = get_teardown_commands(),
    measure_java:run_java_commands(false, 50, SetupCommands,
        lists:flatten(RunCommands), TearDownCommands).

measure_size(N) ->
    N.

get_run_commands(N) ->
    RunCommands = ["{",
        "try {"
            "VoDKATVChannels v = VoDKATVChannels.getInstance();",
            "v.findChannelDatas(" ++ integer_to_list(N) ++ ");",
        "} catch(Exception e) {",
            "e.printStackTrace();",
        "}",
    "}"],
    lists:flatten(RunCommands).

get_setup_commands() ->
    SetupCommands = ["{",
        "try {"
            "VoDKATVChannels v = VoDKATVChannels.getInstance();",
            "v.setUp();",
        "} catch(Exception e) {",
            "e.printStackTrace();",
        "}",
        "return null;",
    "}"],
    lists:flatten(SetupCommands).

get_teardown_commands() ->
    TearDownCommands = ["{",
        "try {"
            "VoDKATVChannels v = VoDKATVChannels.getInstance();",
            "v.tearDown();",
        "} catch(Exception e) {",
            "e.printStackTrace();",
        "}",
        "return null;",
    "}"],
    lists:flatten(TearDownCommands).

global_setup() ->
    Commands = ["{",
        "try {"
            "VoDKATVChannels v = VoDKATVChannels.getInstance();",
            "v.prepareEnvironment();",
        "} catch(Exception e) {",
            "e.printStackTrace();",
        "}",
        "return null;",
    "}"],
    %measure_java:run_java_commands(false, 1, null, lists:flatten(Commands), null),
    ok.

global_teardown() ->
    ok.

measure() ->
    java:set_timeout(infinity),
    ClassPaths = ["../examples/vodkatv/src/", "../examples/vodkatv/vodkatv/"] ++
            filelib:wildcard("../examples/vodkatv/lib/*.jar"),
    Family = #family{initial = 0, grow = fun num_channels/1},
    Axes = #axes{size = fun measure_size/1,
                time = fun eval_cmds/1,
                repeat = 5},
    {Time, _} = timer:tc(measure_java, measure_java,
        [1,  100, Family, Axes, ClassPaths,
        fun global_setup/0, fun global_teardown/0]),
    Time / 1000000.
